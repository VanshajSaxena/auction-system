package com.auction.system.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.auction.system.entities.UserEntity;
import com.auction.system.entities.UserEntity.ApplicationAuthProvider;
import com.auction.system.generated.models.TokensDto;
import com.auction.system.generated.models.UserLoginRequestDto;
import com.auction.system.repositories.UserRepository;
import com.auction.system.services.AuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final UserRepository userRepository;

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiryInMs}")
  private Long jwtExpiryMs;

  @Override
  public TokensDto authenticateWithGoogle(Jwt jwt) {
    String email = jwt.getClaimAsString("email");
    String subject = jwt.getClaimAsString("sub");

    Optional<UserEntity> optionalUserEntity = userRepository.findByGoogleSubId(subject);

    if (optionalUserEntity.isPresent()) {
      UserEntity userEntity = optionalUserEntity.get();
      UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUsername());
      String accessToken = generateToken(userDetails);

      return TokensDto.builder()
          .accessToken(accessToken)
          .refreshToken(null)
          .expiresIn(jwtExpiryMs.intValue())
          .build();
    } else {
      String firstName = jwt.getClaimAsString("given_name");
      String lastName = jwt.getClaimAsString("family_name");
      UserEntity userEntity = UserEntity.builder()
          .username(generateGoogleAuthUsername(email, subject))
          .firstName(firstName)
          .lastName(lastName)
          .email(email)
          .provider(ApplicationAuthProvider.GOOGLE)
          .googleSubId(subject)
          .password(null) // no password for auth provider google.
          .build();

      UserEntity savedUser = userRepository.save(userEntity);
      UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
      String accessToken = generateToken(userDetails);

      return TokensDto.builder()
          .accessToken(accessToken)
          .refreshToken(null)
          .expiresIn(jwtExpiryMs.intValue())
          .build();
    }
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
        .issuer("https://github.com/VanshajSaxena")
        .claims(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
        .signWith(getSigningKey(), Jwts.SIG.HS256)
        .compact();
  }

  @Override
  public UserDetails authenticate(UserLoginRequestDto userLoginRequestDto) {
    String username = userLoginRequestDto.getUsername();
    String email = userLoginRequestDto.getEmail();
    String password = userLoginRequestDto.getPassword();

    boolean isUsernamePresent = username != null && !username.trim().isEmpty();
    boolean isEmailPresent = email != null && !email.trim().isEmpty();

    if (!isUsernamePresent && !isEmailPresent) {
      throw new ValidationException("Either username or password must be provided.");
    }

    if (isUsernamePresent) {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      return userDetailsService.loadUserByUsername(username);
    }

    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Email '%s' not found.".formatted(email)));

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getUsername(), password));
    return userDetailsService.loadUserByUsername(userEntity.getUsername());
  }

  @Override
  public UserDetails validateToken(String token) {
    String username = extractUsername(token);
    return userDetailsService.loadUserByUsername(username);
  }

  @Override
  public Long getJwtExpiryMs() {
    return jwtExpiryMs;
  }

  private String generateGoogleAuthUsername(String email, String subject) {
    return email.substring(0, email.indexOf('@')) +
        subject.substring(subject.length() - 7);
  }

  private String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = secretKey.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }

}

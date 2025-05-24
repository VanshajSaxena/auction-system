package com.auction.system.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auction.system.entities.UserEntity;
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

  @Value("${jwt.expiryInMs:900000}") // default 15 min
  private Long jwtExpiryMs;

  @Override
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
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
        .orElseThrow(() -> new UsernameNotFoundException("Email not found."));

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

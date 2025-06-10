package com.auction.system.services.impl;

import java.util.Optional;

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
import com.auction.system.services.TokenService;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final TokenService tokenService;

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
  public TokensDto authenticateWithGoogle(Jwt jwt) {
    String subject = jwt.getClaimAsString("sub");
    String givenName = jwt.getClaimAsString("given_name");
    String familyName = jwt.getClaimAsString("family_name");
    String email = jwt.getClaimAsString("email");

    Optional<UserEntity> optionalUserEntity = userRepository.findByGoogleSubId(subject);

    UserDetails userDetails;
    if (!optionalUserEntity.isPresent()) {
      UserEntity entity = UserEntity.builder()
          .firstName(givenName)
          .lastName(familyName)
          .username(generateGoogleAuthUsername(email, subject))
          .password(null)
          .email(email)
          .googleSubId(subject)
          .provider(ApplicationAuthProvider.GOOGLE)
          .build();

      UserEntity savedUserEntity = userRepository.save(entity);
      userDetails = userDetailsService.loadUserByUsername(savedUserEntity.getUsername());
    } else {
      userDetails = userDetailsService.loadUserByUsername(optionalUserEntity.get().getUsername());
    }

    String accessToken = tokenService.generateToken(userDetails);

    return TokensDto.builder()
        .accessToken(accessToken)
        .refreshToken(null)
        .expiresIn(tokenService.getJwtExpiryMs().intValue())
        .build();
  }

  private String generateGoogleAuthUsername(String email, String subject) {
    return email.substring(0, email.indexOf('@')) +
        subject.substring(subject.length() - 7);
  }

}

package com.auction.system.service.impl;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.system.entity.AuthProviderEnity;
import com.auction.system.entity.AuthProviderEnity.ProviderEnum;
import com.auction.system.entity.UserEntity;
import com.auction.system.generated.models.TokensDto;
import com.auction.system.generated.models.UserLoginRequestDto;
import com.auction.system.repository.AuthProviderRepository;
import com.auction.system.repository.UserRepository;
import com.auction.system.service.AuthenticationService;
import com.auction.system.service.TokenService;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final TokenService tokenService;

  private final AuthProviderRepository authProviderRepository;

  @Override
  @Transactional
  public TokensDto authenticate(UserLoginRequestDto userLoginRequestDto) {
    String username = userLoginRequestDto.getUsername();
    String email = userLoginRequestDto.getEmail();
    String password = userLoginRequestDto.getPassword();

    boolean isUsernamePresent = username != null && !username.trim().isEmpty();
    boolean isEmailPresent = email != null && !email.trim().isEmpty();

    if (!isUsernamePresent && !isEmailPresent) {
      throw new ValidationException("Either username or email must be provided.");
    }

    if (!isUsernamePresent) {
      UserEntity userEntity = userRepository.findByEmail(email)
          .orElseThrow(() -> new UsernameNotFoundException("Email '%s' not found.".formatted(email)));
      username = userEntity.getUsername();
    }

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    String accessToken = tokenService.generateToken(userDetails);

    return TokensDto.builder()
        .accessToken(accessToken)
        .expiresIn(tokenService.getJwtExpiryMs())
        .build();
  }

  @Override
  @Transactional
  public TokensDto authenticateWithGoogle(Jwt jwt) {
    String subject = jwt.getClaimAsString("sub");
    String givenName = jwt.getClaimAsString("given_name");
    String familyName = jwt.getClaimAsString("family_name");
    String email = jwt.getClaimAsString("email");

    Optional<AuthProviderEnity> optionalAuthProviderEntity = authProviderRepository.findByProviderAndSubId(
        ProviderEnum.GOOGLE,
        subject);

    UserEntity userEntity;
    if (optionalAuthProviderEntity.isPresent()) {
      userEntity = optionalAuthProviderEntity.get().getUser();
    } else {

      Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);

      if (optionalUserEntity.isPresent()) {
        userEntity = optionalUserEntity.get();
        AuthProviderEnity localProvider = AuthProviderEnity.builder()
            .provider(ProviderEnum.GOOGLE)
            .subId(subject)
            .build();
        userEntity.addAuthProvider(localProvider);
      } else {

        userEntity = UserEntity.builder()
            .firstName(givenName)
            .lastName(familyName)
            .username(generateGoogleAuthUsername(email, subject))
            .password(null)
            .email(email)
            .build();

        AuthProviderEnity providerEntity = AuthProviderEnity.builder()
            .provider(ProviderEnum.GOOGLE)
            .subId(subject)
            .build();

        userEntity.addAuthProvider(providerEntity);
        userRepository.save(userEntity);
      }
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUsername());

    String accessToken = tokenService.generateToken(userDetails);

    return TokensDto.builder()
        .accessToken(accessToken)
        .refreshToken(null)
        .expiresIn(tokenService.getJwtExpiryMs())
        .build();
  }

  private String generateGoogleAuthUsername(String email, String subject) {
    return email.substring(0, email.indexOf('@')) +
        subject.substring(subject.length() - 7);
  }

}

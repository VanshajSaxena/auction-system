package com.auction.system.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.system.exception.InternalJwtException;
import com.auction.system.generated.controllers.AuthApiDelegate;
import com.auction.system.generated.models.TokensDto;
import com.auction.system.generated.models.UserLoginRequestDto;
import com.auction.system.generated.models.UserRegistrationRequestDto;
import com.auction.system.generated.models.UserRegistrationResponseDto;
import com.auction.system.service.AuthenticationService;
import com.auction.system.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiDelegateImpl implements AuthApiDelegate {

  private final AuthenticationService authenticationService;

  private final UserService userService;

  @Override
  public ResponseEntity<TokensDto> login(UserLoginRequestDto userLoginRequestDto) {
    TokensDto tokensDto = authenticationService.authenticate(userLoginRequestDto);

    return ResponseEntity.ok(tokensDto);
  }

  @Override
  public ResponseEntity<UserRegistrationResponseDto> register(UserRegistrationRequestDto userRegistrationRequestDto) {
    UserRegistrationResponseDto registrationResponse = userService.registerUser(userRegistrationRequestDto);
    registrationResponse.setMessage("Registration successfull. Please proceed to login.");
    registrationResponse.setSuccess(true);
    var response = ResponseEntity.created(URI.create("/api/v1/users/" + registrationResponse.getUserId()))
        .body(registrationResponse);
    return response;
  }

  @Override
  public ResponseEntity<TokensDto> authenticateWithGoogle() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
      Jwt jwt = (Jwt) authentication.getPrincipal();
      TokensDto tokensDto = authenticationService.authenticateWithGoogle(jwt);
      return ResponseEntity.ok(tokensDto);
    }
    // This should not happen, if it does there's something wrong with
    // resource server filter configuration.
    throw new InternalJwtException("Unauthorized: Invalid or missing JWT principal.");
  }
}

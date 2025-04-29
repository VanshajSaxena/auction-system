package com.auction.auction_system.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.auction_system.generated.controllers.AuthApiDelegate;
import com.auction.auction_system.generated.models.TokensDto;
import com.auction.auction_system.generated.models.UserLoginRequestDto;
import com.auction.auction_system.generated.models.UserRegistrationRequestDto;
import com.auction.auction_system.generated.models.UserRegistrationResponseDto;
import com.auction.auction_system.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthApiDelegateImpl implements AuthApiDelegate {

  private final AuthenticationService authenticationService;

  @Override
  @PostMapping("/auth/login")
  public ResponseEntity<TokensDto> login(UserLoginRequestDto userLoginRequestDto) {
    UserDetails userDetails = authenticationService.authenticate(
        userLoginRequestDto.getUsername(),
        userLoginRequestDto.getEmail(),
        userLoginRequestDto.getPassword());

    String accessToken = authenticationService.generateToken(userDetails);

    return ResponseEntity.ok(TokensDto.builder().accessToken(accessToken).build());
  }

  @Override
  @PostMapping("/auth/register")
  public ResponseEntity<UserRegistrationResponseDto> register(UserRegistrationRequestDto userRegistrationRequestDto) {
    // TODO: Implement after security configuration.
    return AuthApiDelegate.super.register(userRegistrationRequestDto);
  }

}

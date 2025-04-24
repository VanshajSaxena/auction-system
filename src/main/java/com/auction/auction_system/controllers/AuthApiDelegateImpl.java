package com.auction.auction_system.controllers;

import org.springframework.http.ResponseEntity;

import com.auction.auction_system.generated.controllers.AuthApiDelegate;
import com.auction.auction_system.generated.models.UserLoginRequestDto;
import com.auction.auction_system.generated.models.UserLoginResponseDto;
import com.auction.auction_system.generated.models.UserRegistrationRequestDto;
import com.auction.auction_system.generated.models.UserRegistrationResponseDto;

public class AuthApiDelegateImpl implements AuthApiDelegate {

  @Override
  public ResponseEntity<UserLoginResponseDto> login(UserLoginRequestDto userLoginRequestDto) {
    // TODO: Implement after security configuration.
    return AuthApiDelegate.super.login(userLoginRequestDto);
  }

  @Override
  public ResponseEntity<UserRegistrationResponseDto> register(UserRegistrationRequestDto userRegistrationRequestDto) {
    // TODO: Implement after security configuration.
    return AuthApiDelegate.super.register(userRegistrationRequestDto);
  }

}

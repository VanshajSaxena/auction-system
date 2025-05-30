package com.auction.system.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.system.generated.controllers.AuthApiDelegate;
import com.auction.system.generated.models.TokensDto;
import com.auction.system.generated.models.UserLoginRequestDto;
import com.auction.system.generated.models.UserRegistrationRequestDto;
import com.auction.system.generated.models.UserRegistrationResponseDto;
import com.auction.system.services.AuthenticationService;
import com.auction.system.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiDelegateImpl implements AuthApiDelegate {

  private final AuthenticationService authenticationService;

  private final UserService userService;

  @Override
  @PostMapping("/login")
  public ResponseEntity<TokensDto> login(UserLoginRequestDto userLoginRequestDto) {
    UserDetails userDetails = authenticationService.authenticate(userLoginRequestDto);

    String accessToken = authenticationService.generateToken(userDetails);
    Integer expiresIn = authenticationService.getJwtExpiryMs().intValue();

    return ResponseEntity.ok(TokensDto.builder()
        .accessToken(accessToken)
        .expiresIn(expiresIn)
        .build());
  }

  @Override
  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponseDto> register(UserRegistrationRequestDto userRegistrationRequestDto) {
    UserRegistrationResponseDto registrationResponse = userService.registerUser(userRegistrationRequestDto);
    registrationResponse.setMessage("Registration successfull. Please proceed to login.");
    registrationResponse.setSuccess(true);
    var response = ResponseEntity.created(URI.create("/api/v1/users/" + registrationResponse.getUserId()))
        .body(registrationResponse);
    return response;
  }

}

package com.auction.system.service;

import org.springframework.security.oauth2.jwt.Jwt;

import com.auction.system.generated.models.TokensDto;
import com.auction.system.generated.models.UserLoginRequestDto;

public interface AuthenticationService {

  TokensDto authenticate(UserLoginRequestDto userLoginRequestDto);

  TokensDto authenticateWithGoogle(Jwt jwt);
}

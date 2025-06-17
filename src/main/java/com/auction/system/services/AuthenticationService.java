package com.auction.system.services;

import org.springframework.security.oauth2.jwt.Jwt;

import com.auction.system.generated.models.TokensDto;
import com.auction.system.generated.models.UserLoginRequestDto;

public interface AuthenticationService {

  TokensDto authenticate(UserLoginRequestDto userLoginRequestDto);

  TokensDto authenticateWithGoogle(Jwt jwt);
}

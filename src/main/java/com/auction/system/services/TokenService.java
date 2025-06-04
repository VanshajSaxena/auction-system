package com.auction.system.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

import com.auction.system.generated.models.TokensDto;
import com.auction.system.generated.models.UserLoginRequestDto;

public interface TokenService {

  UserDetails authenticate(UserLoginRequestDto userLoginRequestDto);

  String generateToken(UserDetails userDetails);

  UserDetails validateToken(String token);

  Long getJwtExpiryMs();

  TokensDto authenticateWithGoogle(Jwt jwt);
}

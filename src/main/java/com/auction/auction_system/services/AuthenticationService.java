package com.auction.auction_system.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.auction.auction_system.generated.models.UserLoginRequestDto;

public interface AuthenticationService {

  UserDetails authenticate(UserLoginRequestDto userLoginRequestDto);

  String generateToken(UserDetails userDetails);

  UserDetails validateToken(String token);

  Long getJwtExpiryMs();
}

package com.auction.system.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

  String generateToken(UserDetails userDetails);

  Boolean validateToken(String token);

  Integer getJwtExpiryMs();

  String extractUsername(String token);

  boolean validateToken(String token, UserDetails userDetails);
}

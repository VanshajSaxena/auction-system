package com.auction.system.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

  String generateToken(UserDetails userDetails);

  UserDetails validateToken(String token);

  Long getJwtExpiryMs();
}

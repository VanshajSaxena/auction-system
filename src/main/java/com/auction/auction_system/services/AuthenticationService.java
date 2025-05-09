package com.auction.auction_system.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

  UserDetails authenticate(String username, String email, String password);

  String generateToken(UserDetails userDetails);

  UserDetails validateToken(String token);

  Long getJwtExpiryMs();
}

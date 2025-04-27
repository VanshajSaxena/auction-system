package com.auction.auction_system.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

  UserDetails authenticateWithUsername(String username, String password);

  UserDetails authenticateWithEmail(String email, String password);

  String generateToken(UserDetails userDetails);
}

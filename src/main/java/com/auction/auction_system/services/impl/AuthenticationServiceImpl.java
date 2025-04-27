package com.auction.auction_system.services.impl;

import org.springframework.security.core.userdetails.UserDetails;

import com.auction.auction_system.services.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {

  @Override
  public UserDetails authenticateWithUsername(String username, String password) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'authenticateWithUsername'");
  }

  @Override
  public UserDetails authenticateWithEmail(String email, String password) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'authenticateWithEmail'");
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
  }

}

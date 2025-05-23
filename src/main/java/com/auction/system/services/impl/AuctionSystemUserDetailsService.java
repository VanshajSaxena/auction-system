package com.auction.system.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auction.system.entities.UserEntity;
import com.auction.system.repositories.UserRepository;
import com.auction.system.security.AuctionSystemUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuctionSystemUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    return new AuctionSystemUserDetails(userEntity);
  }

}

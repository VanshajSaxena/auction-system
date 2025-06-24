package com.auction.system.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auction.system.entity.UserEntity;
import com.auction.system.exception.UserNotAuthenticatedException;
import com.auction.system.security.AuctionSystemUserDetails;
import com.auction.system.service.CurrentUserService;

@Service
public class DefaultCurrentUserService implements CurrentUserService {

  @Override
  public AuctionSystemUserDetails getUserDetails() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UserNotAuthenticatedException("The user is not authenticated.");
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof AuctionSystemUserDetails) {
      return (AuctionSystemUserDetails) principal;
    } else {
      throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    }
  }

  @Override
  public UserEntity getUserEntity() {
    return this.getUserDetails().getUser();
  }

}

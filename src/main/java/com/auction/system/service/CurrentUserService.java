package com.auction.system.service;

import com.auction.system.entity.UserEntity;
import com.auction.system.security.AuctionSystemUserDetails;

public interface CurrentUserService {

  AuctionSystemUserDetails getUserDetails();

  UserEntity getUserEntity();
}

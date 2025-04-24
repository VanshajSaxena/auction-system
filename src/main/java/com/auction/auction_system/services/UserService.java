package com.auction.auction_system.services;

import java.util.List;

import com.auction.auction_system.generated.models.UserDto;

public interface UserService {
  List<UserDto> getAllUsers();
}

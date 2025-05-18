package com.auction.auction_system.services;

import java.util.List;

import com.auction.auction_system.generated.models.UserDto;
import com.auction.auction_system.generated.models.UserRegistrationRequestDto;
import com.auction.auction_system.generated.models.UserRegistrationResponseDto;

public interface UserService {
  List<UserDto> getAllUsers();

  UserRegistrationResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto);
}

package com.auction.system.service;

import java.util.List;

import com.auction.system.generated.models.UserDto;
import com.auction.system.generated.models.UserRegistrationRequestDto;
import com.auction.system.generated.models.UserRegistrationResponseDto;

public interface UserService {
  List<UserDto> getAllUsers();

  UserRegistrationResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto);
}

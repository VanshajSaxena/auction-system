package com.auction.auction_system.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auction.auction_system.generated.models.UserDto;
import com.auction.auction_system.mappers.UserMapper;
import com.auction.auction_system.repositories.UserRepository;
import com.auction.auction_system.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  @Override
  public List<UserDto> getAllUsers() {
    return userMapper.toDtoList(userRepository.findAll());
  }

}

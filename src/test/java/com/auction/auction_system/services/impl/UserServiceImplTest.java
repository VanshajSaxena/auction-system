package com.auction.auction_system.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auction.auction_system.entities.UserEntity;
import com.auction.auction_system.generated.models.UserDto;
import com.auction.auction_system.mappers.UserMapper;
import com.auction.auction_system.repositories.UserRepository;
import com.auction.auction_system.testutils.UserServiceTestDataFactory;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  UserServiceImpl underTest;

  @Test
  public void whenGetAllUsers_thenReturnAllUsers() {

    List<UserEntity> mockEntityList = UserServiceTestDataFactory.createUserEntityList(5);
    List<UserDto> mockDtoList = UserServiceTestDataFactory.createUserDtoList(5);

    when(userRepository.findAll()).thenReturn(mockEntityList);
    when(userMapper.toDtoList(mockEntityList)).thenReturn(mockDtoList);

    List<UserDto> result = underTest.getAllUsers();

    assertEquals(mockDtoList, result);
    verify(userRepository).findAll();
    verify(userMapper).toDtoList(mockEntityList);
  }
}

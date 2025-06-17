package com.auction.system.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auction.system.entity.UserEntity;
import com.auction.system.exception.EmailAlreadyExistsException;
import com.auction.system.exception.UsernameAlreadyExistsException;
import com.auction.system.generated.models.UserDto;
import com.auction.system.generated.models.UserRegistrationRequestDto;
import com.auction.system.generated.models.UserRegistrationResponseDto;
import com.auction.system.mapper.UserMapper;
import com.auction.system.repository.UserRepository;
import com.auction.system.testutils.UserServiceTestDataFactory;

@ExtendWith(MockitoExtension.class)
public class DefaultUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  DefaultUserService underTest;

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

  @Test
  public void givenValidRegistrationDto_whenRegisterUser_returnSaveUserAndReturnResponse() {
    // Arrange
    UserRegistrationRequestDto requestDto = UserServiceTestDataFactory.createUserRegistrationRequestDto();
    UserEntity userEntity = UserServiceTestDataFactory.createUserEntityFromUserRegistrationRequestDto(requestDto);
    UserEntity savedUserEntity = UserServiceTestDataFactory.createSavedUserEntityWithId(userEntity);

    when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.empty());
    when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("encodedPassword");
    when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);

    // Act
    UserRegistrationResponseDto responseDto = underTest.registerUser(requestDto);

    // Assert
    assertNotNull(responseDto.getUserId());
    assertEquals(savedUserEntity.getId(), responseDto.getUserId());
    verify(userRepository).findByUsername(requestDto.getUsername());
    verify(userRepository).findByEmail(requestDto.getEmail());
    verify(passwordEncoder).encode(requestDto.getPassword());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  public void givenExistingUsername_whenRegisterUser_thenThrowUsernameAlreadyExistsException() {
    // Arrange
    UserRegistrationRequestDto requestDto = UserServiceTestDataFactory.createUserRegistrationRequestDto();
    UserEntity existingUserEntity = UserServiceTestDataFactory
        .createUserEntityFromUserRegistrationRequestDto(requestDto);

    when(userRepository.findByUsername(requestDto.getUsername())).thenReturn(Optional.of(existingUserEntity));

    // Act & Assert
    UsernameAlreadyExistsException exception = assertThrows(UsernameAlreadyExistsException.class, () -> {
      underTest.registerUser(requestDto);
    });

    assertEquals("The username '%s' already exists.".formatted(requestDto.getUsername()), exception.getMessage());
    verify(userRepository).findByUsername(requestDto.getUsername());
  }

  @Test
  public void givenExistingEmail_whenRegisterUser_thenThrowEmailAlreadyExistsException() {
    // Arrange
    UserRegistrationRequestDto requestDto = UserServiceTestDataFactory.createUserRegistrationRequestDto();
    UserEntity existingUserEntity = UserServiceTestDataFactory
        .createUserEntityFromUserRegistrationRequestDto(requestDto);

    when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.of(existingUserEntity));

    // Act & Assert
    EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> {
      underTest.registerUser(requestDto);
    });

    assertEquals("The email '%s' already registered.".formatted(requestDto.getEmail()), exception.getMessage());
    verify(userRepository).findByEmail(requestDto.getEmail());
  }
}

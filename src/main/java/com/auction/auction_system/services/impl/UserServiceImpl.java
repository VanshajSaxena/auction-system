package com.auction.auction_system.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auction.auction_system.entities.UserEntity;
import com.auction.auction_system.exception.EmailAlreadyExistsException;
import com.auction.auction_system.exception.UsernameAlreadyExistsException;
import com.auction.auction_system.generated.models.UserDto;
import com.auction.auction_system.generated.models.UserRegistrationRequestDto;
import com.auction.auction_system.generated.models.UserRegistrationResponseDto;
import com.auction.auction_system.mappers.UserMapper;
import com.auction.auction_system.repositories.UserRepository;
import com.auction.auction_system.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;

  @Override
  public List<UserDto> getAllUsers() {
    return userMapper.toDtoList(userRepository.findAll());
  }

  @Override
  @Transactional
  public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto) {
    String username = userRegistrationRequestDto.getUsername();
    String email = userRegistrationRequestDto.getEmail();
    String firstName = userRegistrationRequestDto.getFirstName();
    String lastName = userRegistrationRequestDto.getLastName();
    String password = userRegistrationRequestDto.getPassword();

    Optional<UserEntity> optionalUserMatchingUsername = userRepository.findByUsername(username);

    if (optionalUserMatchingUsername.isPresent()) {
      throw new UsernameAlreadyExistsException(username, "The username '%s' already exists.".formatted(username));
    }

    Optional<UserEntity> optionalUserMatchingEmail = userRepository.findByEmail(email);

    if (optionalUserMatchingEmail.isPresent()) {
      throw new EmailAlreadyExistsException(email, "The email '%s' already registered.".formatted(email));
    }

    UserEntity userEntity = UserEntity.builder()
        .username(username)
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .password(passwordEncoder.encode(password))
        .build();

    UserEntity savedUser = userRepository.save(userEntity);

    return UserRegistrationResponseDto.builder().userId(savedUser.getId()).build();
  }
}

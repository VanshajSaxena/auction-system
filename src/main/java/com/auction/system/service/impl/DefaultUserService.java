package com.auction.system.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auction.system.entity.AuthProviderEnity;
import com.auction.system.entity.AuthProviderEnity.ProviderEnum;
import com.auction.system.entity.RoleEntity;
import com.auction.system.entity.RoleEntity.RoleNameEntityEnum;
import com.auction.system.entity.UserEntity;
import com.auction.system.exception.DefaultRoleDoesNotExistException;
import com.auction.system.exception.EmailAlreadyExistsException;
import com.auction.system.exception.UsernameAlreadyExistsException;
import com.auction.system.generated.models.UserDto;
import com.auction.system.generated.models.UserRegistrationRequestDto;
import com.auction.system.generated.models.UserRegistrationResponseDto;
import com.auction.system.mapper.UserMapper;
import com.auction.system.repository.RoleRepository;
import com.auction.system.repository.UserRepository;
import com.auction.system.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;

  @Override
  public List<UserDto> getAllUsers() {
    return userMapper.toDtoList(userRepository.findAll());
  }

  @Override
  @Transactional
  public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto req) {
    String username = req.getUsername();
    String email = req.getEmail();

    // Separate queries for granular error messages
    userRepository.findByUsername(username).ifPresent(user -> {
      throw new UsernameAlreadyExistsException("The username '%s' already exists.".formatted(username), username);
    });

    // Separate queries for granular error messages
    userRepository.findByEmail(email).ifPresent(user -> {
      throw new EmailAlreadyExistsException("The email '%s' already registered.".formatted(email), email);
    });

    UserEntity userEntity = UserEntity.builder()
        .username(username)
        .firstName(req.getFirstName())
        .lastName(req.getLastName())
        .email(email)
        .password(passwordEncoder.encode(req.getPassword()))
        .build();

    AuthProviderEnity authProvider = AuthProviderEnity.builder()
        .provider(ProviderEnum.LOCAL)
        .build();

    RoleEntity defaultRole = roleRepository.findByName(RoleEntity.RoleNameEntityEnum.ROLE_USER)
        .orElseThrow(() -> new DefaultRoleDoesNotExistException(
            "Default %s role not found in the database, Please initialize roles."
                .formatted(RoleNameEntityEnum.ROLE_USER),
            RoleNameEntityEnum.ROLE_USER.name()));

    userEntity.addAuthProvider(authProvider);
    userEntity.addRole(defaultRole);

    UserEntity savedUser = userRepository.save(userEntity);

    return UserRegistrationResponseDto.builder().userId(savedUser.getId()).build();
  }
}

package com.auction.system.testutils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.auction.system.entity.UserAddressEntity;
import com.auction.system.entity.UserEntity;
import com.auction.system.generated.models.UserAddressDto;
import com.auction.system.generated.models.UserDto;
import com.auction.system.generated.models.UserRegistrationRequestDto;

public class UserServiceTestDataFactory {
  private UserServiceTestDataFactory() {
  }

  public static UserEntity createUserEntity() {
    return UserEntity.builder()
        .id(1L)
        .firstName("Ram")
        .lastName("Mohan")
        .username("rammohan")
        .password("notverysecret")
        .email("rammohan@email.com")
        .contactNumber("9797979797")
        .shippingAddr(UserAddressEntity.builder()
            .row1("123")
            .row2("ABC Street")
            .row3("XYZ City")
            .build())
        .build();
  }

  public static List<UserEntity> createUserEntityList(int count) {
    return IntStream.rangeClosed(1, count).mapToObj(i -> {
      UserEntity userEntity = createUserEntity();
      userEntity.setId((long) i);
      return userEntity;
    }).collect(Collectors.toList());
  }

  public static UserDto createUserDto() {
    return UserDto.builder()
        .id(1L)
        .firstName("Ram")
        .lastName("Mohan")
        .username("rammohan")
        .email("rammohan@email.com")
        .contactNumber("9797979797")
        .shippingAddr(UserAddressDto.builder()
            .row1("123")
            .row2("ABC Street")
            .row3("XYZ City")
            .build())
        .build();
  }

  public static List<UserDto> createUserDtoList(int count) {
    return IntStream.rangeClosed(1, count).mapToObj(i -> {
      UserDto userDto = createUserDto();
      userDto.setId((long) i);
      return userDto;
    }).collect(Collectors.toList());
  }

  public static UserRegistrationRequestDto createUserRegistrationRequestDto() {
    return UserRegistrationRequestDto.builder()
        .firstName("Ram")
        .lastName("Dashrathi")
        .username("dashratram")
        .password("verysecure")
        .email("contact@dashratram.com")
        .build();
  }

  public static UserEntity createUserEntityFromUserRegistrationRequestDto(UserRegistrationRequestDto requestDto) {
    return UserEntity.builder()
        .firstName(requestDto.getFirstName())
        .lastName(requestDto.getLastName())
        .username(requestDto.getUsername())
        .email(requestDto.getEmail())
        .password("encodedPassword")
        .build();
  }

  public static UserEntity createSavedUserEntityWithId(UserEntity userEntity) {
    userEntity.setId(45L);
    return userEntity;
  }

}

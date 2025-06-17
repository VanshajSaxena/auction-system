package com.auction.system.mapper;

import org.mapstruct.Mapper;

import com.auction.system.entity.UserAddressEntity;
import com.auction.system.generated.models.UserAddressDto;

@Mapper(componentModel = "spring") // Generates a Spring Bean
public interface UserAddressMapper {

  UserAddressDto toDto(UserAddressEntity entity);

  UserAddressEntity toEntity(UserAddressDto dto);
}

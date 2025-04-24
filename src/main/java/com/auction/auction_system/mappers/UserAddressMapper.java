package com.auction.auction_system.mappers;

import org.mapstruct.Mapper;

import com.auction.auction_system.entities.UserAddressEntity;
import com.auction.auction_system.generated.models.UserAddressDto;

@Mapper(componentModel = "spring") // Generates a Spring Bean
public interface UserAddressMapper {

  UserAddressDto toDto(UserAddressEntity entity);

  UserAddressEntity toEntity(UserAddressDto dto);
}

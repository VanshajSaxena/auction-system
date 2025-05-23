package com.auction.system.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.auction.system.entities.UserEntity;
import com.auction.system.generated.models.UserDto;

@Mapper(componentModel = "spring", uses = { UserAddressMapper.class }, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserMapper {

  @Mapping(source = "shippingAddr", target = "shippingAddr") // Handled by UserAddressMapper via 'uses'
  @Mapping(source = "roles", target = "roles", ignore = true) // Don't map roles from DTO
  UserDto toDto(UserEntity entity);

  @Mapping(source = "shippingAddr", target = "shippingAddr") // Handled by UserAddressMapper via 'uses'
  @Mapping(source = "roles", target = "roles", ignore = true) // Don't map roles from DTO
  @Mapping(target = "id", source = "id", ignore = true) // Never map ID from DTO
  @Mapping(target = "password", ignore = true) // Password is not in DTO
  @Mapping(target = "createdAt", ignore = true) // Managed by @PrePersist
  @Mapping(target = "updatedAt", ignore = true) // Managed by @PrePersist/@PreUpdate
  @Mapping(target = "auctionListings", ignore = true) // Don't map collections from simple DTO
  @Mapping(target = "ownedItems", ignore = true) // Don't map collections from simple DTO
  @Mapping(target = "placedBids", ignore = true) // Don't map collections from simple DTO
  UserEntity toEntity(UserDto dto);

  List<UserDto> toDtoList(List<UserEntity> userEntityList);

  List<UserEntity> toEntityList(List<UserDto> userEntityList);

}

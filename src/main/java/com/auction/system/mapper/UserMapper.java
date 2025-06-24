package com.auction.system.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.auction.system.entity.UserEntity;
import com.auction.system.generated.models.UserDto;

@Mapper(componentModel = "spring", uses = { UserAddressMapper.class }, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(target = "shippingAddr", source = "shippingAddr") // Handled by UserAddressMapper via 'uses'
  UserDto toDto(UserEntity entity);

  @Mapping(source = "shippingAddr", target = "shippingAddr") // Handled by UserAddressMapper via 'uses'
  @Mapping(target = "id", source = "id", ignore = true) // Never map ID from DTO
  @Mapping(target = "password", ignore = true) // Password is not in DTO
  @Mapping(target = "authProvider", ignore = true) // Managed at service layer
  @Mapping(target = "createdAt", ignore = true) // Managed by @PrePersist
  @Mapping(target = "updatedAt", ignore = true) // Managed by @PrePersist/@PreUpdate
  @Mapping(target = "auctionListings", ignore = true) // Don't map collections from simple DTO
  @Mapping(target = "ownedItems", ignore = true) // Don't map collections from simple DTO
  @Mapping(target = "placedBids", ignore = true) // Don't map collections from simple DTO
  @Mapping(target = "roles", ignore = true) // Do not map roles from DTO to entity, handled by JWT service
  UserEntity toEntity(UserDto dto);

  List<UserDto> toDtoList(List<UserEntity> userEntityList);

  List<UserEntity> toEntityList(List<UserDto> userEntityList);

}

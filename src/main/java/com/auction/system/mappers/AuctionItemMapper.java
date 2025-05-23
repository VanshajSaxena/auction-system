package com.auction.system.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.auction.system.entities.AuctionItemEntity;
import com.auction.system.generated.models.AuctionItemDto;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AuctionItemMapper {

  @Mapping(target = "ownerId", source = "owner.id")
  AuctionItemDto toDto(AuctionItemEntity auctionItemEntity);

}

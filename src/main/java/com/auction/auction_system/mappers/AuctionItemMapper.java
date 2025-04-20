package com.auction.auction_system.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.auction.auction_system.entities.AuctionItemEntity;
import com.auction.auction_system.generated.models.AuctionItemDto;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN)
public interface AuctionItemMapper {

  @Mapping(target = "ownerId", source = "owner.id")
  AuctionItemDto toDto(AuctionItemEntity auctionItemEntity);

}

package com.auction.auction_system.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.auction.auction_system.entities.AuctionItemEntity;
import com.auction.auction_system.generated.models.AuctionItemDto;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN)
public interface AuctionItemMapper {

  AuctionItemDto toDto(AuctionItemEntity auctionItemEntity);

}

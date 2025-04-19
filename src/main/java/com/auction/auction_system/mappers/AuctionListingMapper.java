package com.auction.auction_system.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.auction.auction_system.entities.AuctionListingEntity;
import com.auction.auction_system.generated.models.AuctionListingDto;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN)
public interface AuctionListingMapper {

  AuctionListingDto toDto(AuctionListingEntity auctionListingEntity);

}

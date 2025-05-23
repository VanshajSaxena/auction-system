package com.auction.system.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.auction.system.entities.AuctionListingEntity;
import com.auction.system.generated.models.AuctionListingDto;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.WARN)
public interface AuctionListingMapper {

  @Mapping(target = "creatorId", source = "creator.id")
  AuctionListingDto toDto(AuctionListingEntity auctionListingEntity);

  List<AuctionListingDto> toDtoList(List<AuctionListingEntity> auctionListingEntityList);

  AuctionListingEntity toEntity(AuctionListingDto auctionListingDto);

}

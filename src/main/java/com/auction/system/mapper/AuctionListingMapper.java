package com.auction.system.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.auction.system.entity.AuctionListingEntity;
import com.auction.system.generated.models.AuctionListingDto;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = { AuctionItemMapper.class })
public interface AuctionListingMapper {

  @Mapping(target = "creatorId", source = "creator.id")
  @Mapping(target = "auctionItem", source = "auctionItem")
  @Mapping(target = "auctionListingState", source = "auctionListingState")
  AuctionListingDto toDto(AuctionListingEntity auctionListingEntity);

  List<AuctionListingDto> toDtoList(List<AuctionListingEntity> auctionListingEntityList);

  @Mapping(target = "createdAt", ignore = true) // Handled by JPA
  @Mapping(target = "updatedAt", ignore = true) // Handled by JPA
  @Mapping(target = "creator", ignore = true) // Mapped at service layer
  @Mapping(target = "bids", ignore = true) // Mapped at service layer
  @Mapping(target = "auctionListingState", source = "auctionListingState") // Read-only but mapped at service layer
  AuctionListingEntity toEntity(AuctionListingDto auctionListingDto);

}

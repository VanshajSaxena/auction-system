package com.auction.auction_system.testutils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.auction.auction_system.entities.AuctionListingEntity;
import com.auction.auction_system.entities.enums.AuctionListingStateEnum;
import com.auction.auction_system.generated.models.AuctionListingDto;

public class AuctionListingTestDataFactory {
  private AuctionListingTestDataFactory() {
  }

  public static AuctionListingEntity createEntity() {
    Instant now = Instant.now();
    return AuctionListingEntity.builder()
        .id(1L)
        .auctionListingState(AuctionListingStateEnum.PENDING)
        .startingPrice(new BigDecimal("1000.00"))
        .reservePrice(new BigDecimal("1000.00"))
        .startTime(now)
        .endTime(now.plusSeconds(3600))
        .build();
  }

  public static List<AuctionListingEntity> createEntityList(int count) {
    return IntStream.rangeClosed(1, count)
        .mapToObj(i -> {
          AuctionListingEntity e = createEntity();
          e.setId((long) i);
          return e;
        })
        .collect(Collectors.toList());
  }

  public static AuctionListingDto createDto() {
    Instant now = Instant.now();
    return AuctionListingDto.builder()
        .id(1L)
        .auctionListingState(
            com.auction.auction_system.generated.models.AuctionListingDto.AuctionListingStateEnum.PENDING)
        .startingPrice(new BigDecimal("1000.00"))
        .reservePrice(new BigDecimal("1000.00"))
        .startTime(now)
        .endTime(now.plusSeconds(3600))
        .build();
  }

  public static List<AuctionListingDto> createDtoList(int count) {
    return IntStream.rangeClosed(1, count)
        .mapToObj(i -> {
          AuctionListingDto e = createDto();
          e.setId((long) i);
          return e;
        })
        .collect(Collectors.toList());
  }
}

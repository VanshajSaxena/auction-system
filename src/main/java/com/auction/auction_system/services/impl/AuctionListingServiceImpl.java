package com.auction.auction_system.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auction.auction_system.generated.models.AuctionListingDto;
import com.auction.auction_system.mappers.AuctionListingMapper;
import com.auction.auction_system.repositories.AuctionListingRepository;
import com.auction.auction_system.services.AuctionListingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuctionListingServiceImpl implements AuctionListingService {

  private final AuctionListingRepository auctionListingRepository;

  private final AuctionListingMapper auctionListingMapper;

  @Override
  public List<AuctionListingDto> getAllAuctionListings() {
    return auctionListingMapper.toDtoList(auctionListingRepository.findAll());
  }

  @Override
  public AuctionListingDto createNewAuctionListing(AuctionListingDto auctionListingDto) {
    return null;
  }

}

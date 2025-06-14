package com.auction.system.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auction.system.generated.models.AuctionListingDto;
import com.auction.system.mappers.AuctionListingMapper;
import com.auction.system.repositories.AuctionListingRepository;
import com.auction.system.services.AuctionListingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultAuctionListingService implements AuctionListingService {

  private final AuctionListingRepository auctionListingRepository;

  private final AuctionListingMapper auctionListingMapper;

  @Override
  public List<AuctionListingDto> getAllAuctionListings() {
    return auctionListingMapper.toDtoList(auctionListingRepository.findAll());
  }

  @Override
  public AuctionListingDto createNewAuctionListing(AuctionListingDto auctionListingDto) {
    // TODO: needs implementation
    return null;
  }

}

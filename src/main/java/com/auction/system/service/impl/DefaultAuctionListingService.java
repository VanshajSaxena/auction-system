package com.auction.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auction.system.generated.models.AuctionListingDto;
import com.auction.system.mapper.AuctionListingMapper;
import com.auction.system.repository.AuctionListingRepository;
import com.auction.system.service.AuctionListingService;

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

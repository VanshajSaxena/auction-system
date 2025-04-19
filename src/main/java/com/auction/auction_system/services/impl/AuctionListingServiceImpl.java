package com.auction.auction_system.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.auction.auction_system.entities.AuctionListingEntity;
import com.auction.auction_system.repositories.AuctionListingRepository;
import com.auction.auction_system.services.AuctionListingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuctionListingServiceImpl implements AuctionListingService {

  private final AuctionListingRepository auctionListingRepository;

  @Override
  public List<AuctionListingEntity> getAllAuctionListings() {
    return auctionListingRepository.findAll();
  }

}

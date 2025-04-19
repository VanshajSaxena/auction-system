package com.auction.auction_system.services;

import java.util.List;

import com.auction.auction_system.entities.AuctionListingEntity;

public interface AuctionListingService {
  List<AuctionListingEntity> getAllAuctionListings();
}

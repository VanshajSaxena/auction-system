package com.auction.auction_system.services;

import java.util.List;

import com.auction.auction_system.generated.models.AuctionListingDto;

public interface AuctionListingService {

  List<AuctionListingDto> getAllAuctionListings();

  AuctionListingDto createNewAuctionListing(AuctionListingDto auctionListingEntity);
}

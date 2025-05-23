package com.auction.system.services;

import java.util.List;

import com.auction.system.generated.models.AuctionListingDto;

public interface AuctionListingService {

  List<AuctionListingDto> getAllAuctionListings();

  AuctionListingDto createNewAuctionListing(AuctionListingDto auctionListingEntity);
}

package com.auction.system.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.system.generated.controllers.AuctionsApiDelegate;
import com.auction.system.generated.models.AuctionListingDto;
import com.auction.system.service.AuctionListingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auctions")
@RequiredArgsConstructor
public class AuctionsApiDelegateImpl implements AuctionsApiDelegate {

  private final AuctionListingService auctionListingService;

  @Override
  public ResponseEntity<List<AuctionListingDto>> getAllAuctionListings() {
    List<AuctionListingDto> dtoList = auctionListingService.getAllAuctionListings();
    return ResponseEntity.ok(dtoList);
  }

  @Override
  public ResponseEntity<AuctionListingDto> createAuctionListing(AuctionListingDto auctionListingDto) {
    AuctionListingDto response = auctionListingService.createNewAuctionListing(auctionListingDto);
    return ResponseEntity.created(URI.create("/api/v1/auctions/" + response.getId())).body(response);
  }

}

package com.auction.system.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.system.generated.controllers.AuctionsApiDelegate;
import com.auction.system.generated.models.AuctionListingDto;
import com.auction.system.services.AuctionListingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auctions")
@RequiredArgsConstructor
public class AuctionsApiDelegateImpl implements AuctionsApiDelegate {

  private final AuctionListingService auctionListingService;

  @Override
  @GetMapping()
  public ResponseEntity<List<AuctionListingDto>> getAllAuctionListings() {
    List<AuctionListingDto> dtoList = auctionListingService.getAllAuctionListings();
    return ResponseEntity.ok(dtoList);
  }

  @Override
  @PostMapping()
  public ResponseEntity<AuctionListingDto> createAuctionListing(AuctionListingDto auctionListingDto) {
    // TODO: Implement after security configuration.
    return null;
  }

}

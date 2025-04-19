package com.auction.auction_system.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.auction_system.generated.controllers.AuctionsApiDelegate;
import com.auction.auction_system.generated.models.AuctionListingDto;
import com.auction.auction_system.services.AuctionListingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuctionDelegateImpl implements AuctionsApiDelegate {

  private final AuctionListingService auctionListingService;

  @Override
  @GetMapping(value = "/auctions")
  public ResponseEntity<List<AuctionListingDto>> getAllAuctionListings() {
    return null;
  }

}

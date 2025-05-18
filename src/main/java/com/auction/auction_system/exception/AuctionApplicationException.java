package com.auction.auction_system.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public abstract class AuctionApplicationException extends RuntimeException {

  private String message;

}

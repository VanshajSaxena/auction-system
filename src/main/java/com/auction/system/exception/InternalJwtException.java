package com.auction.system.exception;

import lombok.Getter;

@Getter

public class InternalJwtException extends AuctionApplicationException {

  public InternalJwtException(String message) {
    super(message);
  }
}

package com.auction.system.exception;

public abstract class AuctionApplicationException extends RuntimeException {

  public AuctionApplicationException() {
    super();
  }

  public AuctionApplicationException(String message) {
    super(message);
  }

  public AuctionApplicationException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuctionApplicationException(Throwable cause) {
    super(cause);
  }
}

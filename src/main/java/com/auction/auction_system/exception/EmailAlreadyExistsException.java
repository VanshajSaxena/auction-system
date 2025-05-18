package com.auction.auction_system.exception;

import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends AuctionApplicationException {

  private String email;

  public EmailAlreadyExistsException(String email) {
    super();
    this.email = email;
  }

  public EmailAlreadyExistsException(String email, String message) {
    super(message);
    this.email = email;
  }

}

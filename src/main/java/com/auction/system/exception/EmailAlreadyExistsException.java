package com.auction.system.exception;

import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends AuctionApplicationException {

  private String email;

  public EmailAlreadyExistsException(String message) {
    super(message);
  }

  public EmailAlreadyExistsException(String message, String email) {
    super(message);
    this.email = email;
  }

}

package com.auction.system.exception;

import lombok.Getter;

@Getter
public class UsernameAlreadyExistsException extends AuctionApplicationException {

  private String username;

  public UsernameAlreadyExistsException(String message) {
    super(message);
  }

  public UsernameAlreadyExistsException(String message, String username) {
    super(message);
    this.username = username;
  }

}

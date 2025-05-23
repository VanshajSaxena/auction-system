package com.auction.system.exception;

import lombok.Getter;

@Getter
public class UsernameAlreadyExistsException extends AuctionApplicationException {

  private String username;

  public UsernameAlreadyExistsException(String username) {
    super();
    this.username = username;
  }

  public UsernameAlreadyExistsException(String username, String message) {
    super(message);
    this.username = username;
  }

}

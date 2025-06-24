package com.auction.system.exception;

import lombok.Getter;

@Getter
public class UserNotAuthenticatedException extends AuctionApplicationException {

  private String username;

  public UserNotAuthenticatedException(String message) {
    super(message);
  }

  public UserNotAuthenticatedException(String message, String username) {
    super(message);
    this.username = username;
  }

}

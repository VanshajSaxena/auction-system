package com.auction.system.exception;

import lombok.Getter;

@Getter
public class DefaultRoleDoesNotExistException extends AuctionApplicationException {

  private String role;

  public DefaultRoleDoesNotExistException(String message) {
    super(message);
  }

  public DefaultRoleDoesNotExistException(String message, String role) {
    super(message);
    this.role = role;
  }
}

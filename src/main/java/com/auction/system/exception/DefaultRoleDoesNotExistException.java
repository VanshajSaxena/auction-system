package com.auction.system.exception;

import lombok.Getter;

@Getter
public class DefaultRoleDoesNotExistException extends AuctionApplicationException {

  private String role;

  public DefaultRoleDoesNotExistException(String role) {
    super();
    this.role = role;
  }

  public DefaultRoleDoesNotExistException(String role, String message) {
    super(message);
    this.role = role;
  }
}

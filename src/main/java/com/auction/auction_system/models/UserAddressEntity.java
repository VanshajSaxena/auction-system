package com.auction.auction_system.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserAddressEntity {

  @Column(length = 100)
  String row1;

  @Column(length = 100)
  String row2;

  @Column(length = 100)
  String row3;
}

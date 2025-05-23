package com.auction.system.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserAddressEntity {

  @Column(length = 100)
  String row1;

  @Column(length = 100)
  String row2;

  @Column(length = 100)
  String row3;
}

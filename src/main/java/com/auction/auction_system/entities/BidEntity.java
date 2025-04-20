package com.auction.auction_system.entities;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bids")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BidEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;
  @Column
  private Instant timeCreated;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private BidValidityEntityEnum validity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bidder_user_id", nullable = false)
  private UserEntity bidder;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "auction_listig_id", nullable = false)
  private AuctionListingEntity auctionListing;

  public enum BidValidityEntityEnum {
    VALID, INVALID, RETRACTED
  }
}

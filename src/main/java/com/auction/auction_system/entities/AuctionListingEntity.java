package com.auction.auction_system.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.auction.auction_system.entities.enums.AuctionListingStatus;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "auction_listings")
public class AuctionListingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(precision = 10, scale = 2, nullable = false)
  private BigDecimal startingPrice;

  @Column(precision = 10, scale = 2)
  private BigDecimal reservePrice;

  @Column(nullable = false)
  private LocalDateTime startTime;

  @Column(nullable = false)
  private LocalDateTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private AuctionListingStatus currentStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_user_id", nullable = false)
  private UserEntity creator;

  @OneToMany(mappedBy = "auctionListing", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<BidEntity> bids;

  @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "auction_item_id", unique = true, nullable = false)
  private AuctionItemEntity auctionItem;

}

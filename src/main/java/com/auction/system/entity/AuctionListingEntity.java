package com.auction.system.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "auction_listings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuctionListingEntity {

  public enum AuctionListingStateEntityEnum {
    PENDING, ACTIVE, CLOSED, CANCELLED, COMPLETED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(precision = 10, scale = 2, nullable = false)
  private BigDecimal startingPrice;

  @Column(precision = 10, scale = 2)
  private BigDecimal reservePrice;

  @Column(nullable = false)
  private Instant startTime;

  @Column(nullable = false)
  private Instant endTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private AuctionListingStateEntityEnum auctionListingState;

  @Column(nullable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_user_id", nullable = false)
  private UserEntity creator;

  @OneToMany(mappedBy = "auctionListing", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<BidEntity> bids;

  @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "auction_item_id", unique = true, nullable = false)
  private AuctionItemEntity auctionItem;

  @Override
  public int hashCode() {
    return Objects.hash(id, description, startingPrice, reservePrice, startTime, endTime, auctionListingState,
        createdAt, updatedAt);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AuctionListingEntity)) {
      return false;
    }
    AuctionListingEntity other = (AuctionListingEntity) obj;
    return Objects.equals(id, other.id) && Objects.equals(description, other.description)
        && Objects.equals(startingPrice, other.startingPrice) && Objects.equals(reservePrice, other.reservePrice)
        && Objects.equals(startTime, other.startTime) && Objects.equals(endTime, other.endTime)
        && auctionListingState == other.auctionListingState && Objects.equals(createdAt, other.createdAt)
        && Objects.equals(updatedAt, other.updatedAt);
  }

  @PrePersist
  private void onCreate() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  private void onUpdate() {
    this.updatedAt = Instant.now();
  }
}

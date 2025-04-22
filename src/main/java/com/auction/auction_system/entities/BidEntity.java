package com.auction.auction_system.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

  public enum BidValidityEntityEnum {
    VALID, INVALID, RETRACTED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private BidValidityEntityEnum validity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bidder_user_id", nullable = false)
  private UserEntity bidder;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "auction_listig_id", nullable = false)
  private AuctionListingEntity auctionListing;

  @Override
  public int hashCode() {
    return Objects.hash(id, amount, createdAt, updatedAt, validity);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof BidEntity)) {
      return false;
    }
    BidEntity other = (BidEntity) obj;
    return Objects.equals(id, other.id) && Objects.equals(amount, other.amount)
        && Objects.equals(createdAt, other.createdAt) && Objects.equals(updatedAt, other.updatedAt)
        && validity == other.validity;
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

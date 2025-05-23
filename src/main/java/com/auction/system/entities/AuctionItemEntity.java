package com.auction.system.entities;

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
@Table(name = "auction_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuctionItemEntity {

  public enum AuctionItemConditionEntityEnum {
    NEW_ITEM, USED, REFURBISHED
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(length = 50)
  private String category;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "item_condition", nullable = false, length = 20) // "condition" can't be used as column name for mysql.
  private AuctionItemConditionEntityEnum auctionItemCondition;

  @Column(nullable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_user_id", nullable = false)
  private UserEntity owner;

  @OneToOne(mappedBy = "auctionItem", fetch = FetchType.LAZY)
  private AuctionListingEntity auctionListing;

  @Override
  public int hashCode() {
    return Objects.hash(id, name, category, description, auctionItemCondition, createdAt, updatedAt);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AuctionItemEntity)) {
      return false;
    }
    AuctionItemEntity other = (AuctionItemEntity) obj;
    return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(category, other.category)
        && Objects.equals(description, other.description) && auctionItemCondition == other.auctionItemCondition
        && Objects.equals(createdAt, other.createdAt) && Objects.equals(updatedAt, other.updatedAt);
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

package com.auction.auction_system.entities;

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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(length = 50)
  private String category;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "item_condition", nullable = false, length = 20) // "condition" can't be used as column name for mysql.
  private AuctionItemConditionEntityEnum auctionItemCondition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_user_id", nullable = false)
  private UserEntity owner;

  @OneToOne(mappedBy = "auctionItem", fetch = FetchType.LAZY)
  private AuctionListingEntity auctionListing;

  public enum AuctionItemConditionEntityEnum {
    NEW_ITEM, USED, REFURBISHED
  }
}

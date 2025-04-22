package com.auction.auction_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.auction_system.entities.AuctionItemEntity;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItemEntity, Long> {

}

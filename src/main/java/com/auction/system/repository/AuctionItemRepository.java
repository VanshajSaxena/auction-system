package com.auction.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.system.entity.AuctionItemEntity;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItemEntity, Long> {

}

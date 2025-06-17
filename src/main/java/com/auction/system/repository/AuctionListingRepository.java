package com.auction.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.system.entity.AuctionListingEntity;

@Repository
public interface AuctionListingRepository extends JpaRepository<AuctionListingEntity, Long> {

}

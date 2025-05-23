package com.auction.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.system.entities.AuctionListingEntity;

@Repository
public interface AuctionListingRepository extends JpaRepository<AuctionListingEntity, Long> {

}

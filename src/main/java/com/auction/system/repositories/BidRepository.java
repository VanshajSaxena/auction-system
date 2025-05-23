package com.auction.system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.system.entities.BidEntity;

@Repository
public interface BidRepository extends JpaRepository<BidEntity, Long> {

}

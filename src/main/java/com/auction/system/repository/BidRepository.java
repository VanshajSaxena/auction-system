package com.auction.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.system.entity.BidEntity;

@Repository
public interface BidRepository extends JpaRepository<BidEntity, Long> {

}

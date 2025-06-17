package com.auction.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.system.entity.AuthProviderEnity;
import com.auction.system.entity.AuthProviderEnity.ProviderEnum;

@Repository
public interface AuthProviderRepository extends JpaRepository<AuthProviderEnity, Long> {

  Optional<AuthProviderEnity> findByProviderAndSubId(ProviderEnum provider, String subId);

}

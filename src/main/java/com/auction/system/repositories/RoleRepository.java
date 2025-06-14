package com.auction.system.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.system.entities.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(RoleEntity.RoleNameEntityEnum roleName);
}

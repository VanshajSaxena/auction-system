package com.auction.system.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auction.system.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findByEmail(String email);

}

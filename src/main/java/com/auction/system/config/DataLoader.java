package com.auction.system.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.auction.system.entities.RoleEntity;
import com.auction.system.entities.RoleEntity.RoleNameEntityEnum;
import com.auction.system.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

  private final RoleRepository roleRepository;

  @Override
  public void run(String... args) throws Exception {

    if (roleRepository.findByName(RoleNameEntityEnum.USER).isEmpty()) {
      RoleEntity userRole = RoleEntity.builder()
          .name(RoleNameEntityEnum.USER)
          .build();

      roleRepository.save(userRole);
    }

    if (roleRepository.findByName(RoleNameEntityEnum.ADMIN).isEmpty()) {
      RoleEntity adminRole = RoleEntity.builder()
          .name(RoleNameEntityEnum.ADMIN)
          .build();

      roleRepository.save(adminRole);
    }
  }

}

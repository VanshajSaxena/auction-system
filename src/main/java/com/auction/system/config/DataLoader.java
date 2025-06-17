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

    if (roleRepository.findByName(RoleNameEntityEnum.ROLE_USER).isEmpty()) {
      RoleEntity userRole = RoleEntity.builder()
          .name(RoleNameEntityEnum.ROLE_USER)
          .build();

      roleRepository.save(userRole);
    }

    if (roleRepository.findByName(RoleNameEntityEnum.ROLE_ADMIN).isEmpty()) {
      RoleEntity adminRole = RoleEntity.builder()
          .name(RoleNameEntityEnum.ROLE_ADMIN)
          .build();

      roleRepository.save(adminRole);
    }
  }

}

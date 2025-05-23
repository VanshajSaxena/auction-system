package com.auction.system.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.system.generated.controllers.UsersApiDelegate;
import com.auction.system.generated.models.UserDto;
import com.auction.system.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UsersApiDelegateImpl implements UsersApiDelegate {

  private final UserService userService;

  @Override
  @GetMapping()
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

}

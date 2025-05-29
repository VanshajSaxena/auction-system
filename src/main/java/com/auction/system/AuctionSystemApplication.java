package com.auction.system;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuctionSystemApplication {

  public static void main(String[] args) {
    System.out.println("Application args: " + Arrays.toString(args));
    SpringApplication.run(AuctionSystemApplication.class, args);
  }

}

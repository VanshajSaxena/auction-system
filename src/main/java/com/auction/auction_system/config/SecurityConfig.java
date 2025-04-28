package com.auction.auction_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auction.auction_system.entities.UserEntity;
import com.auction.auction_system.filters.JwtAuthenticationFilter;
import com.auction.auction_system.repositories.UserRepository;
import com.auction.auction_system.services.AuthenticationService;
import com.auction.auction_system.services.impl.AuctionSystemUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    AuctionSystemUserDetailsService auctionSystemUserDetailsService = new AuctionSystemUserDetailsService(
        userRepository);

    String username = "testuser";
    userRepository.findByUsername(username).orElseGet(() -> {
      UserEntity newUser = UserEntity.builder()
          .firstName("Test User")
          .lastName(" Test User's Last Name")
          .email("testuser@email.com")
          .password(passwordEncoder().encode("notverysecure"))
          .build();
      return userRepository.save(newUser);
    });

    return auctionSystemUserDetailsService;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
    return new JwtAuthenticationFilter(authenticationService);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
      throws Exception {
    return http.authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/auctions/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/v1/bids/**").permitAll())
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}

package com.auction.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfig {

  @Bean
  @Order(1)
  public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http)
      throws Exception {
    return http
        .securityMatcher("/api/v1/auth/google/callback", "/api/v1/auth/apple/callback")
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer((oauth2) -> oauth2
            .jwt((jwt) -> jwt
                .decoder(JwtDecoders.fromIssuerLocation("https://accounts.google.com"))))
        .build();
  }
}

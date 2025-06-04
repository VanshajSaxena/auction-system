package com.auction.system.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.auction.system.services.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultTokenService implements TokenService {

  private final UserDetailsService userDetailsService;

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiryInMs}")
  private Long jwtExpiryMs;

  @Override
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
        .issuer("https://github.com/VanshajSaxena")
        .claims(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
        .signWith(getSigningKey(), Jwts.SIG.HS256)
        .compact();
  }

  @Override
  public UserDetails validateToken(String token) {
    String username = extractUsername(token);
    return userDetailsService.loadUserByUsername(username);
  }

  @Override
  public Long getJwtExpiryMs() {
    return jwtExpiryMs;
  }

  private String extractUsername(String token) {
    return extractAllClaims(token).getSubject();
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = secretKey.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }

}

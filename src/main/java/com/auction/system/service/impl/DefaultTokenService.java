package com.auction.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auction.system.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultTokenService implements TokenService {

  @Value("${app.jwt.secret}")
  private String secretKey;

  @Value("${app.jwt.expiryMs}")
  private Integer jwtExpiryMs;

  @Value("${app.jwt.issuer}")
  private String jwtIssuer;

  @Override
  public String generateToken(UserDetails userDetails) {
    List<Object> authorities = userDetails.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    return Jwts.builder()
        .issuer(jwtIssuer)
        .claim("roles", authorities)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
        .signWith(getSigningKey(), Jwts.SIG.HS256)
        .compact();
  }

  @Override
  public boolean validateToken(String token, UserDetails userDetails) {
    return extractIssuer(token).equals(jwtIssuer)
        && extractUsername(token).equals(userDetails.getUsername())
        && !isTokenExpired(token);
  }

  @Override
  public Boolean validateToken(String token) {
    return extractIssuer(token).equals(jwtIssuer)
        && !isTokenExpired(token);
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public Integer getJwtExpiryMs() {
    return jwtExpiryMs;
  }

  public Date extractIssuedAt(String token) {
    return extractClaim(token, Claims::getIssuedAt);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractIssuer(String token) {
    return extractClaim(token, Claims::getIssuer);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  private SecretKey getSigningKey() {
    byte[] secretBytes = secretKey.getBytes();
    return Keys.hmacShaKeyFor(secretBytes);
  }

}

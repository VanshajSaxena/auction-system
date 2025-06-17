package com.auction.system.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "auth_providers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthProviderEnity {

  public enum ProviderEnum {
    GOOGLE, APPLE, LOCAL
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider", nullable = false)
  ProviderEnum provider;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  UserEntity user;

  @Column
  String subId;

  @Override
  public int hashCode() {
    return Objects.hash(id, provider, subId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof AuthProviderEnity)) {
      return false;
    }
    AuthProviderEnity other = (AuthProviderEnity) obj;
    return Objects.equals(id, other.id) && provider == other.provider && Objects.equals(subId, other.subId);
  }

}

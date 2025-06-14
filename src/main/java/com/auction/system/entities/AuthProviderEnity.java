package com.auction.system.entities;

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

  // Custom equals and hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    AuthProviderEnity that = (AuthProviderEnity) o;

    // For new entities, compare based on provider and subId
    // For persisted entities, compare based on id (if it exists)
    if (id != null && that.id != null) {
      return id.equals(that.id);
    }

    // Ensure provider and subId are not null before comparison
    if (provider == null || that.provider == null || subId == null || that.subId == null) {
      return false; // Or handle as per your logic if nulls are expected in specific cases
    }

    return provider == that.provider && subId.equals(that.subId);
  }

  @Override
  public int hashCode() {
    // For new entities, use provider and subId. For persisted, use id.
    if (id != null) {
      return id.hashCode();
    }

    if (provider == null || subId == null) {
      return super.hashCode(); // Fallback for uninitialized or problematic cases
    }

    return Objects.hash(provider, subId);
  }
}

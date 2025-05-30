package com.auction.system.entities;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

  public enum ApplicationAuthProvider {
    GOOGLE, APPLE, LOCAL
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column
  private String password;

  @Enumerated(EnumType.STRING)
  @Column
  private ApplicationAuthProvider provider;

  @Column
  private String contactNumber;

  @Embedded
  private UserAddressEntity shippingAddr;

  @Column(nullable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Builder.Default
  private Set<RoleEntity> roles = new HashSet<>();

  @OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<AuctionListingEntity> auctionListings;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<AuctionItemEntity> ownedItems;

  @OneToMany(mappedBy = "bidder", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private List<BidEntity> placedBids;

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, username, email,
        provider, password, contactNumber, shippingAddr, createdAt, updatedAt);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof UserEntity)) {
      return false;
    }
    UserEntity other = (UserEntity) obj;
    return Objects.equals(id, other.id) && Objects.equals(firstName, other.firstName)
        && Objects.equals(lastName, other.lastName) && Objects.equals(username, other.username)
        && Objects.equals(email, other.email) && Objects.equals(password, other.password)
        && Objects.equals(contactNumber, other.contactNumber) && Objects.equals(shippingAddr, other.shippingAddr)
        && Objects.equals(createdAt, other.createdAt) && Objects.equals(updatedAt, other.updatedAt)
        && Objects.equals(provider, other.provider);
  }

  @PrePersist
  private void onCreate() {
    Instant now = Instant.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  private void onUpdate() {
    this.createdAt = Instant.now();
  }
}

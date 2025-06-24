package com.auction.system.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.system.entity.AuctionListingEntity;
import com.auction.system.entity.UserEntity;
import com.auction.system.exception.UserNotAuthenticatedException;
import com.auction.system.generated.models.AuctionListingDto;
import com.auction.system.generated.models.AuctionListingDto.AuctionListingStateEnum;
import com.auction.system.mapper.AuctionListingMapper;
import com.auction.system.repository.AuctionListingRepository;
import com.auction.system.repository.UserRepository;
import com.auction.system.security.AuctionSystemUserDetails;
import com.auction.system.service.AuctionListingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultAuctionListingService implements AuctionListingService {

  private final AuctionListingRepository auctionListingRepository;

  private final UserRepository userRepository;

  private final AuctionListingMapper auctionListingMapper;

  @Override
  @Transactional(readOnly = true)
  public List<AuctionListingDto> getAllAuctionListings() {
    return auctionListingMapper.toDtoList(auctionListingRepository.findAll());
  }

  @Override
  @Transactional
  public AuctionListingDto createNewAuctionListing(AuctionListingDto auctionListingDto) {
    // Get authentication
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Defensive check before proceeding
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UserNotAuthenticatedException("The user is not authenticated.");
    }

    Object principal = authentication.getPrincipal();
    UserEntity userEntity;

    // Get user details
    if (principal instanceof AuctionSystemUserDetails) {
      AuctionSystemUserDetails userDetails = (AuctionSystemUserDetails) principal;
      // PERF: There is no need to query the database again as the principal already
      // contains the `UserEntity` object.
      userEntity = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UserNotAuthenticatedException(
          "User with ID '%s' not found in database.".formatted(userDetails.getId())));
      auctionListingDto.setAuctionListingState(AuctionListingStateEnum.PENDING);
    } else {
      throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    }

    if (auctionListingDto.getStartTime() != null && auctionListingDto.getStartTime().isBefore(Instant.now()) &&
        auctionListingDto.getEndTime() != null && auctionListingDto.getEndTime().isAfter(Instant.now())) {
      auctionListingDto.setAuctionListingState(AuctionListingStateEnum.ACTIVE);
    }

    AuctionListingEntity auctionListingEntity = auctionListingMapper.toEntity(auctionListingDto);

    auctionListingEntity.setCreator(userEntity);

    if (auctionListingEntity.getAuctionItem() != null) {
      auctionListingEntity.getAuctionItem().setOwner(userEntity);
    } else {
      // This should not happen if the DTO is valid and mapping layer is working
      // correctly.
      throw new IllegalStateException("The Auction Item cannot be null while creating the auction listing.");
    }

    AuctionListingEntity savedEntity = auctionListingRepository.save(auctionListingEntity);

    return auctionListingMapper.toDto(savedEntity);
  }

}

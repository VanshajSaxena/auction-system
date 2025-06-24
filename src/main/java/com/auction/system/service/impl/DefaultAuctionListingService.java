package com.auction.system.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.system.entity.AuctionListingEntity;
import com.auction.system.entity.UserEntity;
import com.auction.system.generated.models.AuctionListingDto;
import com.auction.system.generated.models.AuctionListingDto.AuctionListingStateEnum;
import com.auction.system.mapper.AuctionListingMapper;
import com.auction.system.repository.AuctionListingRepository;
import com.auction.system.service.AuctionListingService;
import com.auction.system.service.CurrentUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultAuctionListingService implements AuctionListingService {

  private final AuctionListingRepository auctionListingRepository;

  private final CurrentUserService currentUserService;

  private final AuctionListingMapper auctionListingMapper;

  @Override
  @Transactional(readOnly = true)
  public List<AuctionListingDto> getAllAuctionListings() {
    return auctionListingMapper.toDtoList(auctionListingRepository.findAll());
  }

  @Override
  @Transactional
  public AuctionListingDto createNewAuctionListing(AuctionListingDto auctionListingDto) {
    UserEntity userEntity = currentUserService.getUserEntity();

    auctionListingDto.setAuctionListingState(AuctionListingStateEnum.PENDING);

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

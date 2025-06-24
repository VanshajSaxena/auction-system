package com.auction.system.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auction.system.entity.AuctionListingEntity;
import com.auction.system.generated.models.AuctionListingDto;
import com.auction.system.mapper.AuctionListingMapper;
import com.auction.system.repository.AuctionListingRepository;
import com.auction.system.testutil.AuctionListingTestDataFactory;

@ExtendWith(MockitoExtension.class)
public class DefaultAuctionListingServiceTest {

  @Mock
  private AuctionListingRepository auctionListingRepository;

  @Mock
  private AuctionListingMapper auctionListingMapper;

  @InjectMocks
  private DefaultAuctionListingService underTest;

  @Test
  public void whenGetAllAuctionListings_thenReturnAllAuctionListings() {

    List<AuctionListingEntity> mockEntityList = AuctionListingTestDataFactory.createEntityList(5);
    List<AuctionListingDto> mockDtoList = AuctionListingTestDataFactory.createDtoList(5);

    when(auctionListingRepository.findAll()).thenReturn(mockEntityList);
    when(auctionListingMapper.toDtoList(mockEntityList)).thenReturn(mockDtoList);

    List<AuctionListingDto> result = underTest.getAllAuctionListings();

    assertEquals(mockDtoList, result);
    verify(auctionListingRepository).findAll();
    verify(auctionListingMapper).toDtoList(mockEntityList);
  }
}

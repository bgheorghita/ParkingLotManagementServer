package com.basware.services.taxes.prices.impl;

import com.basware.models.parkings.spots.ParkingSpotType;
import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.exceptions.ResourceNotFoundException;
import com.basware.repositories.ParkingSpotTypePriceDao;
import com.basware.services.taxes.prices.ParkingSpotPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingSpotPriceServiceImplTest {

    @Mock
    private ParkingSpotTypePriceDao parkingSpotTypePriceDao;
    @InjectMocks
    private ParkingSpotPriceService parkingSpotPriceService;

    @BeforeEach
    void setUp() {
        parkingSpotPriceService = new ParkingSpotPriceServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPrice_ShouldThrowResourceNotFoundExceptionWhenPriceForParkingSpotTypeSmallNotFound() {
        when(parkingSpotTypePriceDao.findByParkingSpotType(ParkingSpotType.SMALL)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> parkingSpotPriceService.getPrice(ParkingSpotType.SMALL));
        verify(parkingSpotTypePriceDao, times(1)).findByParkingSpotType(ParkingSpotType.SMALL);
    }

    @Test
    void getPrice_ShouldReturnPriceForParkingSpotTypeSmallWhenThereIsAPriceForThatParkingSpotType() throws ResourceNotFoundException {
        Price price = new Price(20, Currency.EUR);
        when(parkingSpotTypePriceDao.findByParkingSpotType(ParkingSpotType.SMALL)).thenReturn(Optional.of(price));

        Price resultPrice = parkingSpotPriceService.getPrice(ParkingSpotType.SMALL);

        assertEquals(price, resultPrice);
    }
}
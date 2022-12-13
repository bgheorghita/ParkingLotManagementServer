package com.basware.ParkingLotManagementWeb.services.taxes.calculators.implementations.prices.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.impl.UserPriceServiceImpl;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.taxes.UserTypePriceDao;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.UserPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserPriceServiceImplTest {

    @Mock
    private UserTypePriceDao userTypePriceDao;

    @InjectMocks
    private UserPriceService userPriceService;

    @BeforeEach
    void setUp() {
        userPriceService = new UserPriceServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPrice_ShouldThrowResourceNotFoundExceptionWhenThereIsNotAPriceForSearchedUserType(){
        when(userTypePriceDao.findByUserType(UserType.REGULAR)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userPriceService.getPrice(UserType.REGULAR));
        verify(userTypePriceDao, times(1)).findByUserType(UserType.REGULAR);
    }

    @Test
    void getPrice_ShouldReturnPriceWhenThereIsAPriceForSearchedUserType() throws ResourceNotFoundException {
        Price price = new Price(1, Currency.EUR);
        when(userTypePriceDao.findByUserType(UserType.VIP)).thenReturn(Optional.of(price));
        Price resultPrice = userPriceService.getPrice(UserType.VIP);
        assertEquals(price, resultPrice);
        verify(userTypePriceDao, times(1)).findByUserType(UserType.VIP);
    }
}
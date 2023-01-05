package com.basware.ParkingLotManagementWeb.services.taxes.calculators.implementations.prices.impl;

import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.impl.UserTypeDiscountPercentServiceImpl;
import com.basware.ParkingLotManagementWeb.repositories.taxes.UserTypeDiscountPercentDao;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.UserTypeDiscountPercentService;
import com.basware.ParkingLotManagementWeb.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeDiscountPercentServiceImplTest {

    @Mock
    private UserTypeDiscountPercentDao userTypeDiscountPercentDao;

    @InjectMocks
    private UserTypeDiscountPercentService userTypeDiscountPercentService;

    @BeforeEach
    void setUp() {
        userTypeDiscountPercentService = new UserTypeDiscountPercentServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getDiscountPercent_ShouldReturnDefaultDiscountWhenThereIsNotADiscountForSearchedUser(){
        when(userTypeDiscountPercentDao.findByUserType(UserType.REGULAR)).thenReturn(Optional.empty());
        Double discountPercent = userTypeDiscountPercentService.getDiscountPercent(UserType.REGULAR);
        assertEquals(Constants.DEFAULT_USER_DISCOUNT_PERCENT, discountPercent);
        verify(userTypeDiscountPercentDao, times(1)).findByUserType(UserType.REGULAR);
    }

    @Test
    void getDiscountPercent_ShouldReturnDiscountIfThereIsADiscountForSearchedUser() {
        Double discountPercent = 0.5;
        when(userTypeDiscountPercentDao.findByUserType(UserType.REGULAR)).thenReturn(Optional.of(discountPercent));
        Double resultDiscountPercent = userTypeDiscountPercentService.getDiscountPercent(UserType.REGULAR);
        assertEquals(discountPercent, resultDiscountPercent);
        verify(userTypeDiscountPercentDao, times(1)).findByUserType(UserType.REGULAR);
    }
}
package com.basware.ParkingLotManagementServer.services.taxes.prices.impl;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.repositories.taxes.UserTypeDiscountPercentDao;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserDiscountPercentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDiscountPercentServiceImplTest {

    @Mock
    private UserTypeDiscountPercentDao userTypeDiscountPercentDao;

    @InjectMocks
    private UserDiscountPercentService userDiscountPercentService;

    @BeforeEach
    void setUp() {
        userDiscountPercentService = new UserDiscountPercentServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getDiscountPercent_ShouldThrowResourceNotFoundExceptionWhenThereIsNotADiscountForSearchedUser(){
        when(userTypeDiscountPercentDao.findByUserType(UserType.REGULAR)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userDiscountPercentService.getDiscountPercent(UserType.REGULAR));
        verify(userTypeDiscountPercentDao, times(1)).findByUserType(UserType.REGULAR);
    }

    @Test
    void getDiscountPercent_ShouldReturnDiscountIfThereIsADiscountForSearchedUser() throws ResourceNotFoundException {
        Double discountPercent = 0.5;
        when(userTypeDiscountPercentDao.findByUserType(UserType.REGULAR)).thenReturn(Optional.of(discountPercent));
        Double resultDiscountPercent = userDiscountPercentService.getDiscountPercent(UserType.REGULAR);
        assertEquals(discountPercent, resultDiscountPercent);
        verify(userTypeDiscountPercentDao, times(1)).findByUserType(UserType.REGULAR);
    }
}
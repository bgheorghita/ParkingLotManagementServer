package com.basware.services.taxes.prices.impl;

import com.basware.models.users.UserType;
import com.basware.repositories.UserTypeDiscountPercentDao;
import com.basware.services.taxes.prices.UserDiscountPercentService;
import com.basware.utils.Constants;
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
    void getDiscountPercent_ShouldReturnDefaultDiscountWhenThereIsNotADiscountForSearchedUser(){
        when(userTypeDiscountPercentDao.findByUserType(UserType.REGULAR)).thenReturn(Optional.empty());
        Double discountPercent = userDiscountPercentService.getDiscountPercent(UserType.REGULAR);
        assertEquals(Constants.DEFAULT_USER_DISCOUNT_PERCENT, discountPercent);
        verify(userTypeDiscountPercentDao, times(1)).findByUserType(UserType.REGULAR);
    }

    @Test
    void getDiscountPercent_ShouldReturnDiscountIfThereIsADiscountForSearchedUser() {
        Double discountPercent = 0.5;
        when(userTypeDiscountPercentDao.findByUserType(UserType.REGULAR)).thenReturn(Optional.of(discountPercent));
        Double resultDiscountPercent = userDiscountPercentService.getDiscountPercent(UserType.REGULAR);
        assertEquals(discountPercent, resultDiscountPercent);
        verify(userTypeDiscountPercentDao, times(1)).findByUserType(UserType.REGULAR);
    }
}
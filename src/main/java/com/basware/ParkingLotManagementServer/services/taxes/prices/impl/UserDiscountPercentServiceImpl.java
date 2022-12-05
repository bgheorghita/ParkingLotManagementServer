package com.basware.ParkingLotManagementServer.services.taxes.prices.impl;

import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.repositories.taxes.UserTypeDiscountPercentDao;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserDiscountPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.basware.ParkingLotManagementServer.utils.Constants.DEFAULT_USER_DISCOUNT_PERCENT;

@Service
public class UserDiscountPercentServiceImpl implements UserDiscountPercentService {
    @Autowired
    private UserTypeDiscountPercentDao userTypeDiscountPercentDao;

    @Override
    public Double getDiscountPercent(UserType userType) {
        Optional<Double> userDiscountOptional = userTypeDiscountPercentDao.findByUserType(userType);
        return userDiscountOptional.orElse(DEFAULT_USER_DISCOUNT_PERCENT);
    }
}

package com.basware.services.taxes.prices.impl;

import com.basware.models.users.UserType;
import com.basware.services.taxes.prices.UserDiscountPercentService;
import com.basware.repositories.UserTypeDiscountPercentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.basware.utils.Constants.DEFAULT_USER_DISCOUNT_PERCENT;

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

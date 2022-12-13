package com.basware.ParkingLotManagementWeb.services.taxes.prices.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.taxes.UserTypePriceDao;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.UserPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPriceServiceImpl implements UserPriceService {
    @Autowired
    private UserTypePriceDao userTypePriceDao;

    @Override
    public Price getPrice(UserType userType) throws ResourceNotFoundException {
        Optional<Price> userPriceOptional = userTypePriceDao.findByUserType(userType);
        if(userPriceOptional.isPresent()) {
            return userPriceOptional.get();
        } else {
            String msg = "Resource user type \"" + userType + "\" has not been found";
            throw new ResourceNotFoundException(msg);
        }
    }
}

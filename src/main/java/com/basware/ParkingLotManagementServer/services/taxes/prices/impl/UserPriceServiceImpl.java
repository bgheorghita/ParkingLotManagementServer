package com.basware.ParkingLotManagementServer.services.taxes.prices.impl;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.repositories.taxes.UserTypePriceDao;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserPriceService;
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

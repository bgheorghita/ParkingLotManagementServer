package com.basware.ParkingLotManagementServer.services.taxes.prices.impl;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.repositories.taxes.UserTypeDiscountDao;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserDiscountPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDiscountServiceImpl implements UserDiscountPriceService {
    @Autowired
    private UserTypeDiscountDao userTypeDiscountDao;

    @Override
    public Price getPrice(UserType userType) throws ResourceNotFoundException {
        Optional<Price> userDiscountOptional = userTypeDiscountDao.findByUserType(userType);
        if(userDiscountOptional.isPresent()){
            return userDiscountOptional.get();
        } else {
            String msg = "Resource user type \"" + userType + "\" has not been found";
            throw new ResourceNotFoundException(msg);
        }
    }
}

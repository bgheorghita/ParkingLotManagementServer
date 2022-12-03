package com.basware.ParkingLotManagementServer.services.taxes.prices.impl;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.repositories.taxes.UserTypeDiscountPercentDao;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserDiscountPercentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDiscountPercentServiceImpl implements UserDiscountPercentService {
    @Autowired
    private UserTypeDiscountPercentDao userTypeDiscountPercentDao;

    @Override
    public Double getDiscountPercent(UserType userType) throws ResourceNotFoundException {
        Optional<Double> userDiscountOptional = userTypeDiscountPercentDao.findByUserType(userType);
        if(userDiscountOptional.isPresent()){
            return userDiscountOptional.get();
        } else {
            String msg = "Resource user type \"" + userType + "\" has not been found";
            throw new ResourceNotFoundException(msg);
        }
    }
}

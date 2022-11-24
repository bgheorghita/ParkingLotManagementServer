package com.basware.ParkingLotManagementServer.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.services.taxes.prices.discounts.UserDiscountServiceImpl;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingDiscountCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingDiscountCalculatorImpl implements ParkingDiscountCalculator {
    @Autowired
    private UserDiscountServiceImpl userDiscountServiceImpl;

    @Override
    public double getDiscount(double totalPrice, UserType userType) throws ResourceNotFoundException {
        return userDiscountServiceImpl.getPrice(userType).getUnits() * totalPrice;
    }
}

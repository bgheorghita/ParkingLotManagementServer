package com.basware.ParkingLotManagementServer.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserDiscountPriceService;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingDiscountCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingDiscountCalculatorImpl implements ParkingDiscountCalculator {
    @Autowired
    private UserDiscountPriceService userDiscountPriceService;

    @Override
    public double getDiscount(double totalPrice, UserType userType) throws ResourceNotFoundException {
        return userDiscountPriceService.getPrice(userType).getUnits() * totalPrice;
    }
}

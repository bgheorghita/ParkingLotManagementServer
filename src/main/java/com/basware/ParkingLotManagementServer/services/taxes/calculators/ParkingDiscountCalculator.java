package com.basware.ParkingLotManagementServer.services.taxes.calculators;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.users.UserType;

public interface ParkingDiscountCalculator {
    double getDiscount(double totalPrice, UserType userType) throws ResourceNotFoundException;
}

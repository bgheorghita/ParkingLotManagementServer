package com.basware.ParkingLotManagementServer.services.taxes.calculators;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;

public interface ParkingDiscountCalculator {
    Price getDiscount(Price totalPrice, UserType userType, Currency toCurrency) throws ResourceNotFoundException, ServiceNotAvailable;
}

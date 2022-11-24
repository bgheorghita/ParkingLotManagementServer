package com.basware.ParkingLotManagementServer.services.taxes.prices.discounts;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;

public interface UserDiscountPriceService {
    Price getPrice(UserType userType) throws ResourceNotFoundException;
}

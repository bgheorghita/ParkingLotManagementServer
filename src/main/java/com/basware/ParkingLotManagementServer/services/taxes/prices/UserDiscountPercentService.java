package com.basware.ParkingLotManagementServer.services.taxes.prices;

import com.basware.ParkingLotManagementServer.models.users.UserType;

public interface UserDiscountPercentService {
    Double getDiscountPercent(UserType userType);
}

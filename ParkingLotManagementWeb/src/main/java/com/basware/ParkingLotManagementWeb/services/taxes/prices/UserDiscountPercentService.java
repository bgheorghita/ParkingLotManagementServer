package com.basware.ParkingLotManagementWeb.services.taxes.prices;


import com.basware.ParkingLotManagementCommon.models.users.UserType;

public interface UserDiscountPercentService {
    Double getDiscountPercent(UserType userType);
}

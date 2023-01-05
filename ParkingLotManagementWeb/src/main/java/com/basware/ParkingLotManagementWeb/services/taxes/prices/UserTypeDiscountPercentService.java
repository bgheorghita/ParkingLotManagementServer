package com.basware.ParkingLotManagementWeb.services.taxes.prices;


import com.basware.ParkingLotManagementCommon.models.users.UserType;

public interface UserTypeDiscountPercentService {
    Double getDiscountPercent(UserType userType);
}

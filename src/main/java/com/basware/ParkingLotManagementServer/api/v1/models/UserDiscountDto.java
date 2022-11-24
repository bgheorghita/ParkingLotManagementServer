package com.basware.ParkingLotManagementServer.api.v1.models;

import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;

public class UserDiscountDto extends UserPriceDto{
    public UserDiscountDto(UserType userType, Price price) {
        super(userType, price);
    }
}

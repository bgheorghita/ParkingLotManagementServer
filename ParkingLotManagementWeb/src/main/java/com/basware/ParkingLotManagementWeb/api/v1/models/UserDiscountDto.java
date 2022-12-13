package com.basware.ParkingLotManagementWeb.api.v1.models;


import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;

public class UserDiscountDto extends UserPriceDto{
    public UserDiscountDto(UserType userType, Price price) {
        super(userType, price);
    }
}

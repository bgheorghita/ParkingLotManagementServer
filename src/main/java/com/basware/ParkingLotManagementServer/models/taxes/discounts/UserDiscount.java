package com.basware.ParkingLotManagementServer.models.taxes.discounts;

import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.taxes.UserPrice;
import com.basware.ParkingLotManagementServer.models.users.UserType;

public class UserDiscount extends UserPrice {
    public UserDiscount(UserType userType, Price price) {
        super(userType, price);
    }
}

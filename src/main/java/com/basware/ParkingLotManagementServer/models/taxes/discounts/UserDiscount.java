package com.basware.ParkingLotManagementServer.models.taxes.discounts;

import com.basware.ParkingLotManagementServer.models.users.UserType;

public class UserDiscount {
    private double percent;
    private UserType userType;

    public UserDiscount(UserType userType, double percent) {
        this.percent = percent;
        this.userType = userType;
    }

    public double getPercent(){
        return percent;
    }

    public UserType getUserType(){
        return userType;
    }
}

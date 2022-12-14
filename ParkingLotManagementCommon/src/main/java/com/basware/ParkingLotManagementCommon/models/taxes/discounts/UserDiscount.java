package com.basware.ParkingLotManagementCommon.models.taxes.discounts;

import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDiscount {
    public static final String PERCENT_FIELD = "percent";
    public static final String USER_TYPE_FIELD = "userType";

    @JsonProperty(PERCENT_FIELD)
    private final double percent;

    @JsonProperty(USER_TYPE_FIELD)
    private final UserType userType;

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

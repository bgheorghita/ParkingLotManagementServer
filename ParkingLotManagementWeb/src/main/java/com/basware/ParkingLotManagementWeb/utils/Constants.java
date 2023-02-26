package com.basware.ParkingLotManagementWeb.utils;


import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.users.Role;

public class Constants {
    public static int DISCOUNT_AVAILABLE_AFTER_MINUTES = 180;
    public static double DEFAULT_USER_DISCOUNT_PERCENT = 0;
    public static Currency DEFAULT_CURRENCY = Currency.EUR;
    public static Role DEFAULT_ROLE = Role.REGULAR;
    public static int ONE_HOUR_IN_MILLIS = 1000 * 60 * 60;
}

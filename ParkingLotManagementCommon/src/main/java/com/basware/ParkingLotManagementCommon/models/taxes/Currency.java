package com.basware.ParkingLotManagementCommon.models.taxes;

import java.util.Arrays;

public enum Currency {
    EUR,
    RON,
    USD;

    public static boolean containsMember(String currency) {
        return Arrays.toString(Currency.values()).contains(currency.toUpperCase());
    }
}

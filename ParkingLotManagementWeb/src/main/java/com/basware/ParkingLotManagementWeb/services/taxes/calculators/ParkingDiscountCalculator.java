package com.basware.ParkingLotManagementWeb.services.taxes.calculators;

import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;

public interface ParkingDiscountCalculator {
    Price getDiscount(Price totalPrice, UserType userType, Currency toCurrency) throws ServiceNotAvailable;
}

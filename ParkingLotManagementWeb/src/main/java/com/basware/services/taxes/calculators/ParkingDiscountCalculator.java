package com.basware.services.taxes.calculators;

import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import com.basware.exceptions.ServiceNotAvailable;

public interface ParkingDiscountCalculator {
    Price getDiscount(Price totalPrice, UserType userType, Currency toCurrency) throws ServiceNotAvailable;
}

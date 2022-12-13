package com.basware.ParkingLotManagementWeb.services.taxes.convertors;

import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;

public interface CurrencyConverter {
    Price convert(Currency fromCurrency, Currency toCurrency, double amount) throws ServiceNotAvailable;
}

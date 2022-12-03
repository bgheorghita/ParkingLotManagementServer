package com.basware.ParkingLotManagementServer.services.taxes.convertors;

import com.basware.ParkingLotManagementServer.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;

public interface CurrencyConverter {
    Price convert(Currency fromCurrency, Currency toCurrency, double amount) throws ServiceNotAvailable;
}

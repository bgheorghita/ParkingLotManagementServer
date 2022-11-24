package com.basware.ParkingLotManagementServer.services.taxes.convertors;

import com.basware.ParkingLotManagementServer.models.taxes.Currency;

public interface CurrencyConverter {
    double convert(Currency from, Currency to);
}

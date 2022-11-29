package com.basware.ParkingLotManagementServer.services.taxes.convertors;

import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;

import java.util.Optional;

public interface CurrencyConverter {
    Optional<Price> convert(Currency fromCurrency, Currency toCurrency, double amount);
}

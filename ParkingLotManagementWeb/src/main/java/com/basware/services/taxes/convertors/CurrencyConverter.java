package com.basware.services.taxes.convertors;

import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.exceptions.ServiceNotAvailable;

public interface CurrencyConverter {
    Price convert(Currency fromCurrency, Currency toCurrency, double amount) throws ServiceNotAvailable;
}

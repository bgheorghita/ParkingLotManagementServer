package com.basware.services.taxes.calculators.implementations;

import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import com.basware.exceptions.ServiceNotAvailable;
import com.basware.services.taxes.calculators.ParkingDiscountCalculator;
import com.basware.services.taxes.prices.UserDiscountPercentService;
import com.basware.services.taxes.convertors.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingDiscountCalculatorImpl implements ParkingDiscountCalculator {
    @Autowired
    private UserDiscountPercentService userDiscountPercentService;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Override
    public Price getDiscount(Price totalPrice, UserType userType, Currency toCurrency) throws ServiceNotAvailable {
        Double userDiscountPercent = userDiscountPercentService.getDiscountPercent(userType);
        totalPrice = exchangeToCurrency(totalPrice, toCurrency);
        return new Price(userDiscountPercent * totalPrice.getUnits(), toCurrency);
    }

    private Price exchangeToCurrency(Price price, Currency toCurrency) throws ServiceNotAvailable {
        if(price.getCurrency() != toCurrency){
            return currencyConverter.convert(price.getCurrency(), toCurrency, price.getUnits());
        }
        return price;
    }
}

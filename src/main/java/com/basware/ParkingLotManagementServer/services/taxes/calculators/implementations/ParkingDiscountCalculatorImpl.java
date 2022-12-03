package com.basware.ParkingLotManagementServer.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.services.taxes.convertors.CurrencyConverter;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserDiscountPercentService;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingDiscountCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingDiscountCalculatorImpl implements ParkingDiscountCalculator {
    @Autowired
    private UserDiscountPercentService userDiscountPercentService;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Override
    public Price getDiscount(Price totalPrice, UserType userType, Currency toCurrency) throws ResourceNotFoundException, ServiceNotAvailable {
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

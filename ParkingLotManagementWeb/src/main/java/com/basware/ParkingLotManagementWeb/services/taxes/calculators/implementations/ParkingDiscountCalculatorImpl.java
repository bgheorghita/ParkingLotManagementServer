package com.basware.ParkingLotManagementWeb.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.services.taxes.calculators.ParkingDiscountCalculator;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.UserTypeDiscountPercentService;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementWeb.services.taxes.convertors.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingDiscountCalculatorImpl implements ParkingDiscountCalculator {
    @Autowired
    private UserTypeDiscountPercentService userTypeDiscountPercentService;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Override
    public Price getDiscount(Price totalPrice, UserType userType, Currency toCurrency) throws ServiceNotAvailable {
        Double userDiscountPercent = userTypeDiscountPercentService.getDiscountPercent(userType);
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

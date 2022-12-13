package com.basware.services.taxes.calculators.implementations;

import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import com.basware.exceptions.ServiceNotAvailable;
import com.basware.services.taxes.prices.UserDiscountPercentService;
import com.basware.services.taxes.calculators.ParkingDiscountCalculator;
import com.basware.services.taxes.convertors.CurrencyConverter;
import com.basware.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingDiscountCalculatorImplTest {

    @Mock
    private UserDiscountPercentService userDiscountPercentService;

    @Mock
    private CurrencyConverter currencyConverter;

    @InjectMocks
    private ParkingDiscountCalculator parkingDiscountCalculator;


    @BeforeEach
    void setUp() {
        parkingDiscountCalculator = new ParkingDiscountCalculatorImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getDiscount_ShouldReturnDefaultDiscountWhenThereIsNotADiscountForSearchedUserType() throws ServiceNotAvailable {
        when(userDiscountPercentService.getDiscountPercent(UserType.REGULAR)).thenReturn(Constants.DEFAULT_USER_DISCOUNT_PERCENT);
        Price totalPrice = new Price(10, Currency.EUR);
        UserType userType = UserType.REGULAR;
        Currency currency = totalPrice.getCurrency();

        Price expectedDiscount = new Price(totalPrice.getUnits() * Constants.DEFAULT_USER_DISCOUNT_PERCENT, totalPrice.getCurrency());
        Price resultDiscount = parkingDiscountCalculator.getDiscount(totalPrice, userType, currency);

        assertEquals(expectedDiscount.toString(), resultDiscount.toString());
        verify(userDiscountPercentService, times(1)).getDiscountPercent(userType);
    }

    @Test
    void getDiscount_ShouldReturnServiceNotAvailableExceptionWhenThereWasAProblemWithTheExchangeCurrencyServiceLayer() throws ServiceNotAvailable {
        Price totalPrice = new Price(10, Currency.EUR);
        UserType userType = UserType.REGULAR;
        Currency toCurrency = Currency.RON;
        Double discountPercent = 0.5;
        when(userDiscountPercentService.getDiscountPercent(userType)).thenReturn(discountPercent);
        when(currencyConverter.convert(totalPrice.getCurrency(), toCurrency, totalPrice.getUnits())).thenThrow(ServiceNotAvailable.class);

        assertThrows(ServiceNotAvailable.class, () -> parkingDiscountCalculator.getDiscount(totalPrice, userType, toCurrency));

        verify(userDiscountPercentService, times(1)).getDiscountPercent(userType);
        verify(currencyConverter, times(1)).convert(totalPrice.getCurrency(), toCurrency, totalPrice.getUnits());
    }

    @Test
    void getDiscount_ShouldMakeCurrencyExchangeAndReturnPriceWithCurrencyRonWhenCurrencyOfTotalPriceIsEur() throws ServiceNotAvailable {
        Currency eurCurrency = Currency.EUR;
        Currency ronCurrency = Currency.RON;
        Price totalPrice = new Price(10, eurCurrency);
        UserType userType = UserType.REGULAR;
        double userTypeDiscountPercent = 0.1;

        when(userDiscountPercentService.getDiscountPercent(userType)).thenReturn(userTypeDiscountPercent);
        // assume that 10EUR = 50RON
        Price priceAfterExchange = new Price(50, ronCurrency);
        Price expectedDiscount = new Price(userTypeDiscountPercent * priceAfterExchange.getUnits(), ronCurrency);
        when(currencyConverter.convert(eurCurrency, ronCurrency, totalPrice.getUnits())).thenReturn(priceAfterExchange);

        Price resultDiscount = parkingDiscountCalculator.getDiscount(totalPrice, userType, ronCurrency);

        assertEquals(expectedDiscount.toString(), resultDiscount.toString());
        verify(userDiscountPercentService, times(1)).getDiscountPercent(userType);
        verify(currencyConverter, times(1)).convert(eurCurrency, ronCurrency, totalPrice.getUnits());
    }

    @Test
    void getDiscount_ShouldReturnDiscountWithoutMakingACurrencyExchangeWhenThereIsNotACurrencyDifference() throws ServiceNotAvailable {
        Price totalPrice = new Price(10, Currency.EUR);
        UserType userType = UserType.VIP;
        double userTypeDiscount = 0.5;

        when(userDiscountPercentService.getDiscountPercent(userType)).thenReturn(userTypeDiscount);
        Price expectedDiscount = new Price(userTypeDiscount * totalPrice.getUnits(), Currency.EUR);
        Price resultDiscount = parkingDiscountCalculator.getDiscount(totalPrice, userType, Currency.EUR);

        assertEquals(expectedDiscount.toString(), resultDiscount.toString());
        verify(userDiscountPercentService, times(1)).getDiscountPercent(userType);
        verify(currencyConverter, times(0)).convert(Currency.EUR, Currency.EUR, totalPrice.getUnits());
    }
}
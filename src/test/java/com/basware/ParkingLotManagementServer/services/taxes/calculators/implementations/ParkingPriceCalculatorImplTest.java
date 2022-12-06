package com.basware.ParkingLotManagementServer.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingPriceCalculator;
import com.basware.ParkingLotManagementServer.services.taxes.convertors.CurrencyConverter;
import com.basware.ParkingLotManagementServer.services.taxes.prices.ParkingSpotPriceService;
import com.basware.ParkingLotManagementServer.services.taxes.prices.UserPriceService;
import com.basware.ParkingLotManagementServer.services.taxes.prices.VehiclePriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingPriceCalculatorImplTest {

    @Mock
    private UserPriceService userPriceService;

    @Mock
    private VehiclePriceService vehiclePriceService;

    @Mock
    private ParkingSpotPriceService parkingSpotPriceService;

    @Mock
    private CurrencyConverter currencyConverter;

    @InjectMocks
    private ParkingPriceCalculator parkingPriceCalculator;

    @BeforeEach
    void setUp() {
        parkingPriceCalculator = new ParkingPriceCalculatorImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTotalPrice_ShouldThrowResourceNotFoundExceptionWhenThereIsNotAPriceForSearchedVehicleType() throws ResourceNotFoundException {
        UserType searchedUserType = UserType.REGULAR;
        VehicleType searchedVehicleType = VehicleType.MOTORCYCLE;
        ParkingSpotType searchedParkingSpotType = ParkingSpotType.SMALL;
        int parkingTimeInMinutes = 100;

        Price priceForUserType = new Price(0.2, Currency.EUR);
        Price priceForParkingSpotType = new Price(0.3, Currency.EUR);

        when(userPriceService.getPrice(searchedUserType)).thenReturn(priceForUserType);
        when(vehiclePriceService.getPrice(searchedVehicleType)).thenThrow(ResourceNotFoundException.class);
        when(parkingSpotPriceService.getPrice(searchedParkingSpotType)).thenReturn(priceForParkingSpotType);

        assertThrows(ResourceNotFoundException.class, () -> parkingPriceCalculator.getTotalPrice(parkingTimeInMinutes, searchedUserType, searchedVehicleType, searchedParkingSpotType, Currency.EUR));
        verify(vehiclePriceService, times(1)).getPrice(searchedVehicleType);
    }

    @Test
    void getTotalPrice_ShouldThrowServiceNotAvailableExceptionWhenWantedACurrencyExchangeAndThatServiceCouldNotMakeTheExchange() throws ResourceNotFoundException, ServiceNotAvailable {
        UserType searchedUserType = UserType.REGULAR;
        VehicleType searchedVehicleType = VehicleType.MOTORCYCLE;
        ParkingSpotType searchedParkingSpotType = ParkingSpotType.SMALL;
        int parkingTimeInMinutes = 60;
        Currency wantedCurrency = Currency.EUR;
        Currency differentCurrency = Currency.RON;

        Price priceForUserType = new Price(0.2, differentCurrency);
        Price priceForVehicleType = new Price(0.1, wantedCurrency);
        Price priceForParkingSpotType = new Price(0.3, wantedCurrency);


        when(userPriceService.getPrice(searchedUserType)).thenReturn(priceForUserType);
        when(vehiclePriceService.getPrice(searchedVehicleType)).thenReturn(priceForVehicleType);
        when(parkingSpotPriceService.getPrice(searchedParkingSpotType)).thenReturn(priceForParkingSpotType);

        when(currencyConverter.convert(differentCurrency, wantedCurrency, priceForUserType.getUnits())).thenThrow(ServiceNotAvailable.class);

        assertThrows(ServiceNotAvailable.class, () -> parkingPriceCalculator.getTotalPrice(parkingTimeInMinutes, searchedUserType,
                searchedVehicleType, searchedParkingSpotType, wantedCurrency));

        verify(userPriceService, times(1)).getPrice(searchedUserType);
        verify(currencyConverter, times(1)).convert(differentCurrency, wantedCurrency, priceForUserType.getUnits());
    }

    @Test
    void getTotalPrice_ShouldReturnTotalPriceWithoutCurrencyExchangeWhenAllPricesHasTheWantedCurrency() throws ResourceNotFoundException, ServiceNotAvailable {
        UserType searchedUserType = UserType.REGULAR;
        VehicleType searchedVehicleType = VehicleType.MOTORCYCLE;
        ParkingSpotType searchedParkingSpotType = ParkingSpotType.SMALL;
        int parkingTimeInMinutes = 60;
        Currency wantedCurrency = Currency.EUR;

        Price priceForUserType = new Price(0.2, wantedCurrency);
        Price priceForVehicleType = new Price(0.1, wantedCurrency);
        Price priceForParkingSpotType = new Price(0.3, wantedCurrency);

        when(userPriceService.getPrice(searchedUserType)).thenReturn(priceForUserType);
        when(vehiclePriceService.getPrice(searchedVehicleType)).thenReturn(priceForVehicleType);
        when(parkingSpotPriceService.getPrice(searchedParkingSpotType)).thenReturn(priceForParkingSpotType);

        Price expectedPrice = new Price(priceForUserType.getUnits() + priceForVehicleType.getUnits()
                + priceForParkingSpotType.getUnits(), wantedCurrency);

        Price resultPrice = parkingPriceCalculator.getTotalPrice(parkingTimeInMinutes, searchedUserType, searchedVehicleType,
                searchedParkingSpotType, wantedCurrency);

        assertEquals(expectedPrice.toString(), resultPrice.toString());
        verify(userPriceService, times(1)).getPrice(searchedUserType);
        verify(vehiclePriceService, times(1)).getPrice(searchedVehicleType);
        verify(parkingSpotPriceService, times(1)).getPrice(searchedParkingSpotType);
        verify(currencyConverter, times(0)).convert(wantedCurrency, wantedCurrency, expectedPrice.getUnits());
    }

    @ParameterizedTest
    @MethodSource("parkingTimeGeneration")
    void getTotalPrice_ShouldReturnTotalPriceInEurCurrencyOfSumOfPricesAndAccordingToTheParkingTime(int parkingTimeInMinutes, int expectedHoursToPay) throws ResourceNotFoundException, ServiceNotAvailable {
        UserType searchedUserType = UserType.REGULAR;
        VehicleType searchedVehicleType = VehicleType.MOTORCYCLE;
        ParkingSpotType searchedParkingSpotType = ParkingSpotType.SMALL;
        Currency wantedCurrency = Currency.EUR;

        Price priceForUserType = new Price(0.2, wantedCurrency);
        Price priceForVehicleType = new Price(0.1, wantedCurrency);
        Price priceForParkingSpotType = new Price(0.3, wantedCurrency);

        when(userPriceService.getPrice(searchedUserType)).thenReturn(priceForUserType);
        when(vehiclePriceService.getPrice(searchedVehicleType)).thenReturn(priceForVehicleType);
        when(parkingSpotPriceService.getPrice(searchedParkingSpotType)).thenReturn(priceForParkingSpotType);

        Price expectedPrice = new Price((priceForUserType.getUnits() + priceForVehicleType.getUnits()
                + priceForParkingSpotType.getUnits()) * expectedHoursToPay, wantedCurrency);

        Price resultPrice = parkingPriceCalculator.getTotalPrice(parkingTimeInMinutes, searchedUserType, searchedVehicleType,
                searchedParkingSpotType, wantedCurrency);

        assertEquals(expectedPrice.toString(), resultPrice.toString());
        verify(userPriceService, times(1)).getPrice(searchedUserType);
        verify(vehiclePriceService, times(1)).getPrice(searchedVehicleType);
        verify(parkingSpotPriceService, times(1)).getPrice(searchedParkingSpotType);
        verify(currencyConverter, times(0)).convert(wantedCurrency, wantedCurrency, expectedPrice.getUnits());
    }

    @ParameterizedTest
    @MethodSource("parkingTimeGeneration")
    void getTotalPrice_ShouldMakeCurrencyExchangeForAnyPriceWhichIsNotInTheWantedCurrencyAndReturnThePriceInWantedCurrency(int parkingTimeInMinutes, int expectedHoursToPay)
            throws ResourceNotFoundException, ServiceNotAvailable {
        UserType searchedUserType = UserType.REGULAR;
        VehicleType searchedVehicleType = VehicleType.MOTORCYCLE;
        ParkingSpotType searchedParkingSpotType = ParkingSpotType.SMALL;
        Currency wantedCurrency = Currency.RON;
        Currency differentCurrency = Currency.EUR;

        Price priceForUserType = new Price(0.2, differentCurrency);
        Price priceForVehicleType = new Price(0.1, differentCurrency);
        Price priceForParkingSpotType = new Price(0.3, differentCurrency);

        // assume that 1EUR = 5RON
        int oneEURtoRON = 5;
        Price priceForUserTypeAfterExchange = new Price(priceForUserType.getUnits() * oneEURtoRON, wantedCurrency);
        Price priceForVehicleTypeAfterExchange = new Price(priceForVehicleType.getUnits() * oneEURtoRON, wantedCurrency);
        Price priceForParkingSpotTypeAfterExchange = new Price(priceForParkingSpotType.getUnits() * oneEURtoRON, wantedCurrency);

        when(userPriceService.getPrice(searchedUserType)).thenReturn(priceForUserType);
        when(vehiclePriceService.getPrice(searchedVehicleType)).thenReturn(priceForVehicleType);
        when(parkingSpotPriceService.getPrice(searchedParkingSpotType)).thenReturn(priceForParkingSpotType);

        when(currencyConverter.convert(differentCurrency, wantedCurrency, priceForUserType.getUnits())).thenReturn(priceForUserTypeAfterExchange);
        when(currencyConverter.convert(differentCurrency, wantedCurrency, priceForVehicleType.getUnits())).thenReturn(priceForVehicleTypeAfterExchange);
        when(currencyConverter.convert(differentCurrency, wantedCurrency, priceForParkingSpotType.getUnits())).thenReturn(priceForParkingSpotTypeAfterExchange);


        Price expectedTotalPrice = new Price((priceForUserTypeAfterExchange.getUnits() + priceForVehicleTypeAfterExchange.getUnits()
                + priceForParkingSpotTypeAfterExchange.getUnits()) * expectedHoursToPay, wantedCurrency);

        Price resultPrice = parkingPriceCalculator.getTotalPrice(parkingTimeInMinutes, searchedUserType, searchedVehicleType,
                searchedParkingSpotType, wantedCurrency);

        assertEquals(expectedTotalPrice.toString(), resultPrice.toString());
        verify(userPriceService, times(1)).getPrice(searchedUserType);
        verify(vehiclePriceService, times(1)).getPrice(searchedVehicleType);
        verify(parkingSpotPriceService, times(1)).getPrice(searchedParkingSpotType);
        verify(currencyConverter, times(1)).convert(differentCurrency, wantedCurrency, priceForUserType.getUnits());
        verify(currencyConverter, times(1)).convert(differentCurrency, wantedCurrency, priceForVehicleType.getUnits());
        verify(currencyConverter, times(1)).convert(differentCurrency, wantedCurrency, priceForParkingSpotType.getUnits());
    }

    private static Stream<Arguments> parkingTimeGeneration() {
        return Stream.of(
                Arguments.of(100, 2), //100 minutes = 1h and 40min => should pay tax for 2 hours because 40 >= 30 (half of an hour)
                Arguments.of(200, 3), // 3h and 20min
                Arguments.of(10, 1), // what is less than 1 hour should be taxed as one hour
                Arguments.of(70, 1), // 1 hour and 10minutes => should pay tax for 1 hour because 10 minutes < 30min (less than half of an hour)
                Arguments.of(90, 2), // 1h and 30min -> tax for 2h
                Arguments.of(91, 2), // 1h and 31min -> tax for 2h
                Arguments.of(89, 1) // 1h and 29 min -> tax for 1h
        );
    }
}
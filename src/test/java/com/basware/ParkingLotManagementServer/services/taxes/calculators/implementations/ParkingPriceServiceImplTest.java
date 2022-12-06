package com.basware.ParkingLotManagementServer.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingDiscountCalculator;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingPriceCalculator;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingPriceService;
import com.basware.ParkingLotManagementServer.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingPriceServiceImplTest {

    @Mock
    private ParkingPriceCalculator parkingPriceCalculator;

    @Mock
    private ParkingDiscountCalculator parkingDiscountCalculator;

    @InjectMocks
    private ParkingPriceService parkingPriceService;

    @BeforeEach
    void setUp() {
        parkingPriceService = new ParkingPriceServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getParkingPrice_ShouldThrowResourceNotFoundExceptionWhenAPriceIsNotFound() throws ResourceNotFoundException, ServiceNotAvailable {
        UserType userType = UserType.REGULAR;
        VehicleType vehicleType = VehicleType.CAR;
        ParkingSpotType parkingSpotType = ParkingSpotType.MEDIUM;
        int parkingTime = 60;

        when(parkingPriceCalculator.getTotalPrice(parkingTime, userType, vehicleType, parkingSpotType, Currency.EUR))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,
                () -> parkingPriceService.getParkingPrice(parkingTime, userType, vehicleType, parkingSpotType, Currency.EUR));

        verify(parkingPriceCalculator, times(1))
                .getTotalPrice(parkingTime, userType, vehicleType, parkingSpotType, Currency.EUR);
    }

    @Test
    void getParkingPrice_ShouldThrowServiceNotAvailableWhenCurrencyExchangeIsNeededForDiscountCalculationAndServiceCouldNotMakeTheExchange()
            throws ResourceNotFoundException, ServiceNotAvailable {
        UserType userType = UserType.REGULAR;
        VehicleType vehicleType = VehicleType.CAR;
        ParkingSpotType parkingSpotType = ParkingSpotType.MEDIUM;
        int parkingTime = Constants.DISCOUNT_AVAILABLE_AFTER_MINUTES;
        Currency wantedCurrency = Currency.EUR;

        Price returnPrice = new Price(10, wantedCurrency);

        when(parkingPriceCalculator.getTotalPrice(parkingTime, userType, vehicleType, parkingSpotType, wantedCurrency))
                .thenReturn(returnPrice);

        when(parkingDiscountCalculator.getDiscount(returnPrice, userType, wantedCurrency))
                .thenThrow(ServiceNotAvailable.class);

        assertThrows(ServiceNotAvailable.class, () -> parkingPriceService.getParkingPrice(parkingTime,
                userType, vehicleType, parkingSpotType, wantedCurrency));

        verify(parkingPriceCalculator, times(1))
                .getTotalPrice(parkingTime, userType, vehicleType, parkingSpotType, wantedCurrency);
        verify(parkingDiscountCalculator, times(1))
                .getDiscount(returnPrice, userType, wantedCurrency);
    }

    @Test
    void getParkingPrice_ShouldReturnPriceWithoutDiscountWhenTheDiscountIsNotAvailableForTheSpecifiedParkingTime() throws ResourceNotFoundException, ServiceNotAvailable {
        UserType userType = UserType.REGULAR;
        VehicleType vehicleType = VehicleType.CAR;
        ParkingSpotType parkingSpotType = ParkingSpotType.MEDIUM;
        int parkingTime = Constants.DISCOUNT_AVAILABLE_AFTER_MINUTES - 1;
        Currency wantedCurrency = Currency.EUR;

        Price returnPrice = new Price(10, wantedCurrency);
        when(parkingPriceCalculator.getTotalPrice(parkingTime, userType, vehicleType, parkingSpotType, wantedCurrency))
                .thenReturn(returnPrice);

        Price resultPrice = parkingPriceService.getParkingPrice(parkingTime,
                userType, vehicleType, parkingSpotType, wantedCurrency);

        assertEquals(returnPrice.toString(), resultPrice.toString());

        verify(parkingPriceCalculator, times(1))
                .getTotalPrice(parkingTime, userType, vehicleType, parkingSpotType, wantedCurrency);
        verify(parkingDiscountCalculator, times(0))
                .getDiscount(returnPrice, userType, wantedCurrency);
    }

    @Test
    void getParkingPrice_ShouldReturnPriceWithDiscountWhenTheDiscountIsAvailableForTheSpecifiedParkingTime() throws ResourceNotFoundException, ServiceNotAvailable {
        UserType userType = UserType.REGULAR;
        VehicleType vehicleType = VehicleType.CAR;
        ParkingSpotType parkingSpotType = ParkingSpotType.MEDIUM;
        int parkingTime = Constants.DISCOUNT_AVAILABLE_AFTER_MINUTES;
        Currency wantedCurrency = Currency.EUR;

        Price returnPrice = new Price(10, wantedCurrency);
        Price returnDiscount = new Price(5, wantedCurrency);
        Price returnPriceAfterDiscount = new Price(returnPrice.getUnits() - returnDiscount.getUnits(), wantedCurrency);

        when(parkingPriceCalculator.getTotalPrice(parkingTime, userType, vehicleType, parkingSpotType, wantedCurrency))
                .thenReturn(returnPrice);

        when(parkingDiscountCalculator.getDiscount(returnPrice, userType, wantedCurrency))
                .thenReturn(returnDiscount);

        Price resultPrice = parkingPriceService.getParkingPrice(parkingTime,
                userType, vehicleType, parkingSpotType, wantedCurrency);

        assertEquals(returnPriceAfterDiscount.toString(), resultPrice.toString());

        verify(parkingPriceCalculator, times(1))
                .getTotalPrice(parkingTime, userType, vehicleType, parkingSpotType, wantedCurrency);
        verify(parkingDiscountCalculator, times(1))
                .getDiscount(returnPrice, userType, wantedCurrency);
    }
}
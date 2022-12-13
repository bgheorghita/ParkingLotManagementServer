package com.basware.services.taxes.calculators.implementations;

import com.basware.models.parkings.spots.ParkingSpotType;
import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import com.basware.models.vehicles.VehicleType;
import com.basware.exceptions.ResourceNotFoundException;
import com.basware.exceptions.ServiceNotAvailable;
import com.basware.services.taxes.convertors.CurrencyConverter;
import com.basware.services.taxes.prices.ParkingSpotPriceService;
import com.basware.services.taxes.prices.UserPriceService;
import com.basware.services.taxes.prices.VehiclePriceService;
import com.basware.services.taxes.calculators.ParkingPriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingPriceCalculatorImpl implements ParkingPriceCalculator {
    @Autowired
    private UserPriceService userPriceService;
    @Autowired
    private VehiclePriceService vehiclePriceService;
    @Autowired
    private ParkingSpotPriceService parkingSpotPriceService;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Override
    public Price getTotalPrice(int parkingTimeInMinutes, UserType userType, VehicleType vehicleType, ParkingSpotType parkingSpotType, Currency toCurrency) throws ResourceNotFoundException, ServiceNotAvailable {
        int hours = parkingTimeInMinutes / 60;
        int minutes = parkingTimeInMinutes % 60;
        if(minutes >= 30 || (hours == 0 && minutes > 0)){
            hours++;
        } else {
            //TODO: What happens if parkingTimeInMinutes <= 0?
        }

        Price userPrice = exchangeToCurrency(userPriceService.getPrice(userType), toCurrency);
        Price vehiclePrice = exchangeToCurrency(vehiclePriceService.getPrice(vehicleType), toCurrency);
        Price parkingSpotPrice = exchangeToCurrency(parkingSpotPriceService.getPrice(parkingSpotType), toCurrency);

        return new Price((userPrice.getUnits() + vehiclePrice.getUnits() + parkingSpotPrice.getUnits()) * hours, toCurrency);
    }

    private Price exchangeToCurrency(Price price, Currency toCurrency) throws ServiceNotAvailable {
        if(price.getCurrency() != toCurrency){
            return currencyConverter.convert(price.getCurrency(), toCurrency, price.getUnits());
        }
        return price;
    }
}
package com.basware.ParkingLotManagementWeb.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementWeb.services.taxes.convertors.CurrencyConverter;
import com.basware.ParkingLotManagementWeb.services.taxes.calculators.ParkingPriceCalculator;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.TypePriceService;
import org.springframework.stereotype.Component;

@Component
public class ParkingPriceCalculatorImpl implements ParkingPriceCalculator {
    private final TypePriceService typePriceService;
    private final CurrencyConverter currencyConverter;

    public ParkingPriceCalculatorImpl(TypePriceService typePriceService, CurrencyConverter currencyConverter) {
        this.typePriceService = typePriceService;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public Price getTotalPrice(long parkingTimeInMinutes, UserType userType, VehicleType vehicleType, ParkingSpotType parkingSpotType, Currency toCurrency) throws ResourceNotFoundException, ServiceNotAvailable {
        long hours = parkingTimeInMinutes / 60;
        long minutes = parkingTimeInMinutes % 60;
        if(minutes >= 30 || (hours == 0 && minutes > 0)){
            hours++;
        } else {
            //TODO: What happens if parkingTimeInMinutes <= 0?
        }

        Price userPrice = exchangeToCurrency(typePriceService.getPrice(new TypeInfo(TypeInfo.USER_IDENTIFIER, userType.name())), toCurrency);
        Price vehiclePrice = exchangeToCurrency(typePriceService.getPrice(new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, vehicleType.name())), toCurrency);
        Price parkingSpotPrice = exchangeToCurrency(typePriceService.getPrice(new TypeInfo(TypeInfo.PARKING_SPOT_IDENTIFIER, parkingSpotType.name())), toCurrency);

        return new Price((userPrice.getUnits() + vehiclePrice.getUnits() + parkingSpotPrice.getUnits()) * hours, toCurrency);
    }

    private Price exchangeToCurrency(Price price, Currency toCurrency) throws ServiceNotAvailable {
        if(price.getCurrency() != toCurrency){
            return currencyConverter.convert(price.getCurrency(), toCurrency, price.getUnits());
        }
        return price;
    }
}
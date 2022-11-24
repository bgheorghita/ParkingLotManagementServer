package com.basware.ParkingLotManagementServer.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingDiscountCalculator;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingPriceCalculator;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.basware.ParkingLotManagementServer.utils.Constants.DISCOUNT_AVAILABLE_AFTER_MINUTES;

@Service
public class ParkingPriceServiceImpl implements ParkingPriceService {
    @Autowired
    private ParkingPriceCalculator parkingPriceCalculator;
    @Autowired
    private ParkingDiscountCalculator parkingDiscountCalculator;

    @Override
    public Price getParkingPrice(int parkingTimeInMinutes, UserType userType, VehicleType vehicleType,
                                  ParkingSpotType parkingSpotType) throws ResourceNotFoundException {
        double totalPrice = parkingPriceCalculator.getTotalPrice(parkingTimeInMinutes, userType,
                vehicleType, parkingSpotType);

        if(parkingTimeInMinutes > DISCOUNT_AVAILABLE_AFTER_MINUTES){
            totalPrice -= parkingDiscountCalculator.getDiscount(totalPrice, userType);
        }

        return new Price(totalPrice, Currency.EUR);
    }
}

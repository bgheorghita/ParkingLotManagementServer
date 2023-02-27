package com.basware.ParkingLotManagementWeb.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementWeb.services.taxes.calculators.ParkingDiscountCalculator;
import com.basware.ParkingLotManagementWeb.services.taxes.calculators.ParkingPriceCalculator;
import com.basware.ParkingLotManagementWeb.services.taxes.calculators.ParkingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.basware.ParkingLotManagementWeb.utils.Constants.DISCOUNT_AVAILABLE_AFTER_MINUTES;

@Component
public class ParkingPriceServiceImpl implements ParkingPriceService {
    @Autowired
    private ParkingPriceCalculator parkingPriceCalculator;
    @Autowired
    private ParkingDiscountCalculator parkingDiscountCalculator;

    @Override
    public Price getParkingPrice(long parkingTimeInMinutes, UserType userType, VehicleType vehicleType,
                                 ParkingSpotType parkingSpotType, Currency toCurrency)
            throws ResourceNotFoundException, ServiceNotAvailable {

        Price totalPrice = parkingPriceCalculator.getTotalPrice(parkingTimeInMinutes, userType,
                vehicleType, parkingSpotType, toCurrency);

        if(parkingTimeInMinutes >= DISCOUNT_AVAILABLE_AFTER_MINUTES){
            Price discount = parkingDiscountCalculator.getDiscount(totalPrice, userType, toCurrency);
            totalPrice = new Price(totalPrice.getUnits() - discount.getUnits(), toCurrency);
        }

        return totalPrice;
    }
}

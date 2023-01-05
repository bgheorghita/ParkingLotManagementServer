package com.basware.ParkingLotManagementWeb.services.taxes.calculators.implementations;

import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.exceptions.InvalidInput;
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
    public Price getParkingPrice(String parkingTimeInMinutes, String userType, String vehicleType,
                                 String parkingSpotType, String toCurrency)
            throws ResourceNotFoundException, ServiceNotAvailable, InvalidInput {

        checkForBlankInputs(parkingTimeInMinutes, userType, vehicleType, parkingSpotType, toCurrency);
        validateInputs(parkingTimeInMinutes, userType, vehicleType, parkingSpotType, toCurrency);

        int parkingTimeInMinutesInput = Integer.parseInt(parkingTimeInMinutes);
        UserType userTypeInput = UserType.valueOf(userType.toUpperCase());
        VehicleType vehicleTypeInput = VehicleType.valueOf(vehicleType.toUpperCase());
        ParkingSpotType parkingSpotTypeInput = ParkingSpotType.valueOf(parkingSpotType.toUpperCase());
        Currency currencyInput = Currency.valueOf(toCurrency.toUpperCase());

        Price totalPrice = parkingPriceCalculator.getTotalPrice(parkingTimeInMinutesInput, userTypeInput,
                vehicleTypeInput, parkingSpotTypeInput, currencyInput);

        if(parkingTimeInMinutesInput >= DISCOUNT_AVAILABLE_AFTER_MINUTES){
            Price discount = parkingDiscountCalculator.getDiscount(totalPrice, userTypeInput, currencyInput);
            totalPrice = new Price(totalPrice.getUnits() - discount.getUnits(), currencyInput);
        }

        return totalPrice;
    }

    private void checkForBlankInputs(String parkingTimeInMinutes, String userType, String vehicleType, String parkingSpotType, String toCurrency) throws InvalidInput {
        String[] inputs = new String[]{parkingTimeInMinutes, userType, vehicleType, parkingSpotType, toCurrency};
        for(String input : inputs){
            if(input.isBlank()){
                throw new InvalidInput("Inputs are not allowed to be blank or empty");
            }
        }
    }
    private void validateInputs(String parkingTimeInMinutes, String userType, String vehicleType, String parkingSpotType,
                                String toCurrency) throws ResourceNotFoundException, InvalidInput {

        if(!parkingTimeInMinutes.matches("[0-9]+")){
            throw new InvalidInput("Only digits are allowed for parking time in minutes.");
        }

        if(!UserType.containsMember(userType)){
            throw new ResourceNotFoundException("User type \"" + userType + "\" not found.");
        }

        if(!VehicleType.containsMember(vehicleType)){
            System.out.println("verific: " + vehicleType);
            throw new ResourceNotFoundException("Vehicle type \"" + vehicleType + "\" not found.");
        }

        if(!ParkingSpotType.containsMember(parkingSpotType)){
            throw new ResourceNotFoundException("Parking spot type \"" + parkingSpotType + "\" not found.");
        }

        if(!Currency.containsMember(toCurrency)){
            throw new ResourceNotFoundException("Currency \"" + toCurrency + "\" not available.");
        }
    }
}

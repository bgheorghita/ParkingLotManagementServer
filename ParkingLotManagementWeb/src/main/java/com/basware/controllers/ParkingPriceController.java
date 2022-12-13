package com.basware.controllers;

import com.basware.models.parkings.spots.ParkingSpotType;
import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import com.basware.models.vehicles.VehicleType;
import com.basware.exceptions.ResourceNotFoundException;
import com.basware.exceptions.ServiceNotAvailable;
import com.basware.services.taxes.calculators.ParkingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.basware.utils.Constants.DEFAULT_CURRENCY;

@RestController
@RequestMapping(ParkingPriceController.URL_BASE)
public class ParkingPriceController {
    public static final String URL_BASE = "api/v1/taxes";
    @Autowired
    private ParkingPriceService parkingPriceService;

    @GetMapping("/getParkingPrice")
    @ResponseStatus(HttpStatus.OK)
    public Price getParkingPrice(@RequestParam("parkingTimeInMinutes") String parkingTimeInMinutes,
                                 @RequestParam("userType") String userType,
                                 @RequestParam("vehicleType") String vehicleType,
                                 @RequestParam("parkingSpotType") String parkingSpotType,
                                 @RequestParam(required = false, name = "toCurrency") String toCurrency) throws ResourceNotFoundException, ServiceNotAvailable {

        if(toCurrency == null){
            toCurrency = DEFAULT_CURRENCY.name();
        }

//        if(!ParkingSpotType.containsMember(parkingSpotType)){
//            throw new ResourceNotFoundException(parkingSpotType + " does not exist.");
//        }

        return parkingPriceService.getParkingPrice(Integer.parseInt(parkingTimeInMinutes), UserType.valueOf(userType),
                VehicleType.valueOf(vehicleType), ParkingSpotType.valueOf(parkingSpotType), Currency.valueOf(toCurrency));
    }
}

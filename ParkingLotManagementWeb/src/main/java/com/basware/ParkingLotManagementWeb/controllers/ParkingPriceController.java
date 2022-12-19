package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementWeb.exceptions.InvalidInput;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementWeb.services.taxes.calculators.ParkingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.basware.ParkingLotManagementWeb.utils.Constants.DEFAULT_CURRENCY;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
                                 @RequestParam(required = false, name = "toCurrency") String toCurrency)
                                 throws ResourceNotFoundException, ServiceNotAvailable, InvalidInput {

        if(toCurrency == null || toCurrency.isBlank()){
            toCurrency = DEFAULT_CURRENCY.name();
        }

        return parkingPriceService.getParkingPrice(parkingTimeInMinutes, userType, vehicleType, parkingSpotType, toCurrency);
    }
}

package com.basware.ParkingLotManagementServer.controllers.taxes;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.ParkingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ParkingPriceController.URL_BASE)
public class ParkingPriceController {
    public static final String URL_BASE = "/taxes";
    @Autowired
    private ParkingPriceService parkingPriceService;

//    @GetMapping("/getParkingPrice")
//    public double getParkingPrice(@RequestParam("userType") UserType userType,
//                                  @RequestParam("vehicleType") VehicleType vehicleType,
//                                  @RequestParam("parkingSpotType") ParkingSpotType parkingSpotType){
//        return parkingPriceService.getParkingPrice(userType, vehicleType, parkingSpotType);
//    }

    @GetMapping("/getParkingPrice")
    @ResponseStatus(HttpStatus.OK)
    public Price getParkingPrice(@RequestParam("parkingTimeInMinutes") String parkingTimeInMinutes,
                                 @RequestParam("userType") String userType,
                                 @RequestParam("vehicleType") String vehicleType,
                                 @RequestParam("parkingSpotType") String parkingSpotType) throws ResourceNotFoundException {

        return parkingPriceService.getParkingPrice(Integer.parseInt(parkingTimeInMinutes), UserType.valueOf(userType),
                VehicleType.valueOf(vehicleType), ParkingSpotType.valueOf(parkingSpotType));
    }
}

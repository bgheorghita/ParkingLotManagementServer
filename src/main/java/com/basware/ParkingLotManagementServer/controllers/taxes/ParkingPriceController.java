package com.basware.ParkingLotManagementServer.controllers.taxes;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.services.taxes.calculators.implementations.ParkingPriceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ParkingPriceController.URL_BASE)
public class ParkingPriceController {
    public static final String URL_BASE = "/taxes";
    @Autowired
    private ParkingPriceServiceImpl parkingPriceServiceImpl;

//    @GetMapping("/getParkingPrice")
//    public double getParkingPrice(@RequestParam("userType") UserType userType,
//                                  @RequestParam("vehicleType") VehicleType vehicleType,
//                                  @RequestParam("parkingSpotType") ParkingSpotType parkingSpotType){
//        return parkingPriceService.getParkingPrice(userType, vehicleType, parkingSpotType);
//    }

    @GetMapping("/getParkingPrice")
    @ResponseStatus(HttpStatus.OK)
    public double getParkingPrice(@RequestParam("parkingTimeInMinutes") String parkingTimeInMinutes,
                                  @RequestParam("userType") String userType,
                                  @RequestParam("vehicleType") String vehicleType,
                                  @RequestParam("parkingSpotType") String parkingSpotType) throws ResourceNotFoundException {

        return parkingPriceServiceImpl.getParkingPrice(Integer.parseInt(parkingTimeInMinutes), UserType.valueOf(userType),
                VehicleType.valueOf(vehicleType), ParkingSpotType.valueOf(parkingSpotType)).getUnits();
    }
}

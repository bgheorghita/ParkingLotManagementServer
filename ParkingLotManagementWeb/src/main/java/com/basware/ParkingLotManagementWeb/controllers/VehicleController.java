package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(VehicleController.BASE_URL)
public class VehicleController {
    public static final String BASE_URL = "/api/v1/vehicles";

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Vehicle> getAllVehicles(){
        return vehicleService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{plateNumber}")
    public Vehicle getVehicleByPlateNumber(@PathVariable String plateNumber) throws ResourceNotFoundException {
        return vehicleService.findByPlateNumber(plateNumber);
    }
}

package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementWeb.services.spots.ParkingSpotService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(ParkingLotController.URL_BASE)
public class ParkingLotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingLotController(ParkingSpotService parkingSpotService){
        this.parkingSpotService = parkingSpotService;
    }
    public static final String URL_BASE = "api/v1/spots";

    @GetMapping
    public List<ParkingSpot> getParkingSpots(){
        return parkingSpotService.findAll();
    }
}

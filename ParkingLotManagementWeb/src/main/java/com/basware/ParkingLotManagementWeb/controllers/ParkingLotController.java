package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementWeb.api.v1.models.ParkingResultDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.services.parking.lots.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;


@RestController
@RequestMapping(ParkingLotController.URL_BASE)
public class ParkingLotController {
    public static final String URL_BASE = "api/v1/lot";

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService){
        this.parkingLotService = parkingLotService;
    }

    @PostMapping("/in/{vehiclePlateNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketOutputDto generateTicket(@PathVariable String vehiclePlateNumber, Principal principal) throws SaveException, TooManyRequestsException, ResourceNotFoundException, UnauthorizedException, VehicleAlreadyParkedException {
        return parkingLotService.generateTicket(principal.getName(), vehiclePlateNumber);
    }

    @PostMapping("/out/{vehiclePlateNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingResultDto leaveParkingLot(@PathVariable String vehiclePlateNumber, Principal principal) throws TicketException, ServiceNotAvailable, ResourceNotFoundException, TooManyRequestsException, SaveException {
        return parkingLotService.leaveParkingLot(principal.getName(), vehiclePlateNumber);
    }
}
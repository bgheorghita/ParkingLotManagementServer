package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketInputDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.ParkingSpotNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementWeb.exceptions.TicketException;
import com.basware.ParkingLotManagementWeb.services.parking.lots.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(ParkingLotController.URL_BASE)
public class ParkingLotController {
    public static final String URL_BASE = "api/v1/lot";

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService){
        this.parkingLotService = parkingLotService;
    }

    @PostMapping("/ticket")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketOutputDto generateTicket(@RequestBody TicketInputDto ticketInputDto) throws SaveException, TicketException, ParkingSpotNotFoundException, ServiceNotAvailable {
        String userName = ticketInputDto.getUserName();
        UserType userType = ticketInputDto.getUserType();
        User user = new User(userName, userType);

        VehicleType vehicleType = ticketInputDto.getVehicleType();
        String vehiclePlateNumber = ticketInputDto.getVehiclePlateNumber();
        boolean vehicleIsElectric = ticketInputDto.vehicleIsElectric();
        Vehicle vehicle =  new Vehicle(vehicleType, vehiclePlateNumber, vehicleIsElectric);

        return parkingLotService.generateTicket(user, vehicle);
    }
}

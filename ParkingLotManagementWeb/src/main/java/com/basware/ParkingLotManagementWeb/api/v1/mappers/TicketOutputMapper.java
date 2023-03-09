package com.basware.ParkingLotManagementWeb.api.v1.mappers;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutput;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class TicketOutputMapper {

    @Autowired
    private VehicleService vehicleService;

    public TicketOutput fromTicketToTicketOutput(Ticket ticket) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(ticket.getVehiclePlateNumber());
        return TicketOutput.builder()
                .vehiclePlateNumber(ticket.getVehiclePlateNumber())
                .electricVehicle(vehicle.getIsElectric())
                .parkingSpotNumber(ticket.getParkingSpotNumber())
                .parkingSpotType(ticket.getParkingSpotType())
                .parkingSpotWithElectricCharger(ticket.getIsParkingSpotWithElectricCharger())
                .timestampParkAt(Timestamp.valueOf(ticket.getStartTime()).getTime())
                .build();
    }
}
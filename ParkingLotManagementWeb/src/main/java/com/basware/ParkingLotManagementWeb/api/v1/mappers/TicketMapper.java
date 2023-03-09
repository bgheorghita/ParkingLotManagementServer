package com.basware.ParkingLotManagementWeb.api.v1.mappers;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutput;

import java.sql.Timestamp;

public class TicketMapper {
    public static TicketOutput fromTicketAndVehicleToTicketOutput(Ticket ticket, Vehicle vehicle){
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

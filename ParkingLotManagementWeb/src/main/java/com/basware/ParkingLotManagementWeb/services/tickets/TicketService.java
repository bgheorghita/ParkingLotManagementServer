package com.basware.ParkingLotManagementWeb.services.tickets;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementWeb.services.CrudService;

import java.util.Optional;

public interface TicketService extends CrudService<Ticket> {
    Optional<Ticket> findTicketByParkingSpotNumber(long parkingSpotNumber);
}

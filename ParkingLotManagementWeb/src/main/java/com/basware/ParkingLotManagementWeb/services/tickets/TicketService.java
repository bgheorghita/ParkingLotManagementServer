package com.basware.ParkingLotManagementWeb.services.tickets;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.services.CrudService;

public interface TicketService extends CrudService<Ticket> {
    Ticket findFirstByVehiclePlateNumber(String vehiclePlateNumber) throws ResourceNotFoundException;
}

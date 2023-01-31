package com.basware.ParkingLotManagementWeb.repositories.tickets;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;

import java.util.List;

public interface TicketDao extends CrudRepository<Ticket> {
    List<Ticket> findByVehiclePlateNumber(String vehiclePlateNumber);
}

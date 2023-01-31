package com.basware.ParkingLotManagementWeb.services.tickets;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.tickets.TicketDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl extends CrudServiceImpl<Ticket> implements TicketService {

    private final TicketDao ticketDao;

    public TicketServiceImpl(TicketDao ticketDao){
        this.ticketDao = ticketDao;
    }

    @Override
    public Ticket findFirstByVehiclePlateNumber(String vehiclePlateNumber) throws ResourceNotFoundException {
        List<Ticket> tickets = ticketDao.findByVehiclePlateNumber(vehiclePlateNumber);
        if(tickets.size() > 0){
            return tickets.get(0);
        }
        throw new ResourceNotFoundException(String.format("Ticket by vehicle plate number \"%s\" could not be found.", vehiclePlateNumber));
    }
}
package com.basware.ParkingLotManagementWeb.services.tickets;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementWeb.repositories.tickets.TicketDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TicketServiceImpl extends CrudServiceImpl<Ticket> implements TicketService {

    private final TicketDao ticketDao;

    public TicketServiceImpl(TicketDao ticketDao){
        this.ticketDao = ticketDao;
    }

    @Override
    public Optional<Ticket> findTicketByParkingSpotNumber(long parkingSpotNumber) {
        ticketDao.findAllByFieldValues(Map.of());
        return Optional.empty();
    }
}
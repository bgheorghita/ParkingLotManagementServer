package com.basware.ParkingLotManagementWeb.services.tickets;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementWeb.repositories.tickets.TicketDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl extends CrudServiceImpl<Ticket> implements TicketService {
    public TicketServiceImpl(TicketDao ticketDao) {
        super(ticketDao);
    }
}

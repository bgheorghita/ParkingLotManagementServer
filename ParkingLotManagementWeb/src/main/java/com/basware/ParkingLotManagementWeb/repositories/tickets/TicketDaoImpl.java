package com.basware.ParkingLotManagementWeb.repositories.tickets;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepositoryImpl;
import dev.morphia.Datastore;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDaoImpl extends CrudRepositoryImpl<Ticket> implements TicketDao {
    public TicketDaoImpl(Datastore datastore) {
        super(Ticket.class, datastore);
    }
}

package com.basware.ParkingLotManagementWeb.repositories.tickets;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepositoryImpl;
import dev.morphia.Datastore;
import org.bson.BsonString;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TicketDaoImpl extends CrudRepositoryImpl<Ticket> implements TicketDao {

    public TicketDaoImpl(Datastore datastore) {
        super(Ticket.class, datastore);
    }

    @Override
    public List<Ticket> findByVehiclePlateNumber(String vehiclePlateNumber) {
        return findAllByFieldValues(Map.of(Ticket.VEHICLE_PLATE_NUMBER_FIELD, new BsonString(vehiclePlateNumber)));
    }
}

package com.basware.ParkingLotManagementWeb.repositories.vehicles;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepositoryImpl;
import dev.morphia.Datastore;
import org.bson.BsonString;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class VehicleDaoImpl extends CrudRepositoryImpl<Vehicle> implements VehicleDao {

    public VehicleDaoImpl(Datastore datastore) {
        super(Vehicle.class, datastore);
    }

    @Override
    public List<Vehicle> findAllByPlateNumber(String plateNumber) {
        return findAllByFieldValues(Map.of(Vehicle.VEHICLE_PLATE_NUMBER_FIELD, new BsonString(plateNumber)));
    }
}

package com.basware.ParkingLotManagementWeb.repositories.parking.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepositoryImpl;
import dev.morphia.Datastore;
import org.bson.BsonBoolean;
import org.bson.BsonString;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ParkingSpotDaoImpl extends CrudRepositoryImpl<ParkingSpot> implements ParkingSpotDao{

    public ParkingSpotDaoImpl(Datastore datastore) {
        super(ParkingSpot.class, datastore);
    }

    @Override
    public List<ParkingSpot> findAllByParkingSpotType(ParkingSpotType parkingSpotType) {
        return findAllByFieldValues(Map.of(ParkingSpot.PARKING_SPOT_TYPE_FIELD,
                new BsonString(parkingSpotType.name())));
    }

    @Override
    public List<ParkingSpot> findAllByElectricCharger(boolean hasElectricCharger) {
        return findAllByFieldValues(Map.of(ParkingSpot.HAS_ELECTRIC_CHARGER_FIELD,
                new BsonBoolean(hasElectricCharger)));
    }

    @Override
    public List<ParkingSpot> findAllFreeByParkingSpotType(ParkingSpotType parkingSpotType) {
        return findAllByFieldValues(Map.of(ParkingSpot.IS_FREE_FIELD, new BsonBoolean(true),
                ParkingSpot.PARKING_SPOT_TYPE_FIELD, new BsonString(parkingSpotType.name())));
    }

    @Override
    public List<ParkingSpot> findAllFree() {
        return findAllByFieldValues(Map.of(ParkingSpot.IS_FREE_FIELD, new BsonBoolean(true)));
    }
}

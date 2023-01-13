package com.basware.ParkingLotManagementWeb.services.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementWeb.repositories.parking.spots.ParkingSpotDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotServiceImpl extends CrudServiceImpl<ParkingSpot> implements ParkingSpotService{

    public ParkingSpotServiceImpl(ParkingSpotDao parkingSpotDao) {
        super(parkingSpotDao);
    }

    @Override
    public List<ParkingSpot> findAllByParkingSpotType(ParkingSpotType parkingSpotType) {
        return ((ParkingSpotDao) crudRepository).findAllByParkingSpotType(parkingSpotType);
    }

    @Override
    public List<ParkingSpot> findAllByElectricCharger(boolean hasElectricCharger) {
        return ((ParkingSpotDao) crudRepository).findAllByElectricCharger(hasElectricCharger);
    }
}

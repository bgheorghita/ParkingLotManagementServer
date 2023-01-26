package com.basware.ParkingLotManagementWeb.services.parking.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementWeb.repositories.parking.spots.ParkingSpotDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotServiceImpl extends CrudServiceImpl<ParkingSpot> implements ParkingSpotService{

    private final ParkingSpotDao parkingSpotDao;

    public ParkingSpotServiceImpl(ParkingSpotDao parkingSpotDao) {
        this.parkingSpotDao = parkingSpotDao;
    }

    @Override
    public List<ParkingSpot> findAllByParkingSpotType(ParkingSpotType parkingSpotType) {
        return parkingSpotDao.findAllByParkingSpotType(parkingSpotType);
    }

    @Override
    public List<ParkingSpot> findAllByElectricCharger(boolean hasElectricCharger) {
        return parkingSpotDao.findAllByElectricCharger(hasElectricCharger);
    }

    @Override
    public List<ParkingSpot> findAllFreeByParkingSpotType(ParkingSpotType parkingSpotType) {
        return parkingSpotDao.findAllFreeByParkingSpotType(parkingSpotType);
    }

    @Override
    public List<ParkingSpot> findAllFree() {
        return parkingSpotDao.findAllFree();
    }
}

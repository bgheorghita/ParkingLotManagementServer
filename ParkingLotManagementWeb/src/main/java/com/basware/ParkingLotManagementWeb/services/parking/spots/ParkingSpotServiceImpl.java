package com.basware.ParkingLotManagementWeb.services.parking.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
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
    public ParkingSpot findFirstByVehiclePlateNumber(String vehiclePlateNumber) throws ResourceNotFoundException {
        List<ParkingSpot> parkingSpots = parkingSpotDao.findAllByVehiclePlateNumber(vehiclePlateNumber);
        if(parkingSpots.size() > 0){
            return parkingSpots.get(0);
        }
        throw new ResourceNotFoundException(String.format("Parking spot by vehicle plate number \"%s\" could not be found", vehiclePlateNumber));
    }

    @Override
    public List<ParkingSpot> findAllFree() {
        return parkingSpotDao.findAllFree();
    }
}

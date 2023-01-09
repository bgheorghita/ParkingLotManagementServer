package com.basware.ParkingLotManagementWeb.services.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService{

    private final CrudRepository<ParkingSpot> parkingSpotCrudRepository;

    public ParkingSpotServiceImpl(CrudRepository<ParkingSpot> parkingSpotCrudRepository){
        this.parkingSpotCrudRepository = parkingSpotCrudRepository;
    }

    @Override
    public boolean save(ParkingSpot parkingSpot) {
        return parkingSpotCrudRepository.save(parkingSpot);
    }
}

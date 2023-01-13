package com.basware.ParkingLotManagementWeb.repositories.parking.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;

import java.util.List;

public interface ParkingSpotDao extends CrudRepository<ParkingSpot> {
    List<ParkingSpot> findAllByParkingSpotType(ParkingSpotType parkingSpotType);
    List<ParkingSpot> findAllByElectricCharger(boolean hasElectricCharger);
}

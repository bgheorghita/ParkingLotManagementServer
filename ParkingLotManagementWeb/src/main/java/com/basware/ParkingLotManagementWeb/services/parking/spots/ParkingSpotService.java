package com.basware.ParkingLotManagementWeb.services.parking.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementWeb.services.CrudService;

import java.util.List;

public interface ParkingSpotService extends CrudService<ParkingSpot> {
    List<ParkingSpot> findAllByParkingSpotType(ParkingSpotType parkingSpotType);
    List<ParkingSpot> findAllByElectricCharger(boolean hasElectricCharger);
    List<ParkingSpot> findAllFreeByParkingSpotType(ParkingSpotType parkingSpotType);
    List<ParkingSpot> findAllFree();
}

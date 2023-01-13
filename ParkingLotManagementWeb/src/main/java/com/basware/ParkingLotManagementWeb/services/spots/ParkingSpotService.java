package com.basware.ParkingLotManagementWeb.services.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementWeb.services.CrudService;

import java.util.List;

public interface ParkingSpotService extends CrudService<ParkingSpot> {
    List<ParkingSpot> findAllByParkingSpotType(ParkingSpotType parkingSpotType);
    List<ParkingSpot> findAllByElectricCharger(boolean hasElectricCharger);
}

package com.basware.ParkingLotManagementWeb.services.parking.spots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.services.CrudService;

import java.util.List;

public interface ParkingSpotService extends CrudService<ParkingSpot> {
    List<ParkingSpot> findAllByParkingSpotType(ParkingSpotType parkingSpotType);
    List<ParkingSpot> findAllByElectricCharger(boolean hasElectricCharger);
    List<ParkingSpot> findAllFreeByParkingSpotType(ParkingSpotType parkingSpotType);
    ParkingSpot findFirstByVehiclePlateNumber(String vehiclePlateNumber) throws ResourceNotFoundException;
    List<ParkingSpot> findAllFree();
}

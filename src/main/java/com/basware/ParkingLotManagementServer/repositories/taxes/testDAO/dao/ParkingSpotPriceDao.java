package com.basware.ParkingLotManagementServer.repositories.taxes.dao;

import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.ParkingSpotPrice;

import java.util.Optional;

public interface ParkingSpotPriceDao {
    Optional<ParkingSpotPrice> findByParkingSpotType(ParkingSpotType parkingSpotType);
}

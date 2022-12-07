package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.ParkingSpotPrice;
import com.basware.ParkingLotManagementServer.models.taxes.Price;

import java.util.Optional;

public interface ParkingSpotTypePriceDao {
    Optional<Price> findByParkingSpotType(ParkingSpotType parkingSpotType);
    boolean save(ParkingSpotPrice parkingSpotPrice);
    long deleteByParkingSpotType(ParkingSpotType parkingSpotType);
    void deleteAll();
}

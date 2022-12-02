package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.ParkingSpotPrice;

import java.util.Optional;

public interface ParkingSpotTypePriceDao {
    Optional<ParkingSpotPrice> findByParkingSpotType(ParkingSpotType parkingSpotType);
    void save(ParkingSpotPrice parkingSpotPrice);
    void delete(ParkingSpotPrice parkingSpotPrice);
    void deleteAll();
}

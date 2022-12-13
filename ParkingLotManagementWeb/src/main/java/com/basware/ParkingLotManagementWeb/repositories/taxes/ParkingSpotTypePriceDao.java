package com.basware.ParkingLotManagementWeb.repositories.taxes;

import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.ParkingSpotPrice;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;

import java.util.Optional;

public interface ParkingSpotTypePriceDao {
    Optional<Price> findByParkingSpotType(ParkingSpotType parkingSpotType);
    boolean save(ParkingSpotPrice parkingSpotPrice);
    long deleteByParkingSpotType(ParkingSpotType parkingSpotType);
    void deleteAll();
}

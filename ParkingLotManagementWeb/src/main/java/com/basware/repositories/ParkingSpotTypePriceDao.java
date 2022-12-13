package com.basware.repositories;

import com.basware.models.parkings.spots.ParkingSpotType;
import com.basware.models.taxes.ParkingSpotPrice;
import com.basware.models.taxes.Price;

import java.util.Optional;

public interface ParkingSpotTypePriceDao {
    Optional<Price> findByParkingSpotType(ParkingSpotType parkingSpotType);
    boolean save(ParkingSpotPrice parkingSpotPrice);
    long deleteByParkingSpotType(ParkingSpotType parkingSpotType);
    void deleteAll();
}

package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.ParkingSpotPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ParkingSpotPriceRepository extends MongoRepository<ParkingSpotPrice, String> {
    Optional<ParkingSpotPrice> findByParkingSpotType(ParkingSpotType parkingSpotType);
}

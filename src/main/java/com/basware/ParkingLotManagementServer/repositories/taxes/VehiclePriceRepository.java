package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.taxes.VehiclePrice;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VehiclePriceRepository extends MongoRepository<VehiclePrice, String> {
    Optional<VehiclePrice> findByVehicleType(VehicleType vehicleType);
}
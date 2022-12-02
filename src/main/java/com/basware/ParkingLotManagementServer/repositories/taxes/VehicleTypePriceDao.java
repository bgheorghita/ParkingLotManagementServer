package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.taxes.VehiclePrice;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;

import java.util.Optional;

public interface VehicleTypePriceDao {
    Optional<VehiclePrice> findByVehicleType(VehicleType vehicleType);
    void save(VehiclePrice vehiclePrice);
    void deleteAll();
}

package com.basware.ParkingLotManagementWeb.repositories.taxes;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.VehiclePrice;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;

import java.util.Optional;

public interface VehicleTypePriceDao {
    Optional<Price> findByVehicleType(VehicleType vehicleType);
    void save(VehiclePrice vehiclePrice);
    void deleteAll();
}

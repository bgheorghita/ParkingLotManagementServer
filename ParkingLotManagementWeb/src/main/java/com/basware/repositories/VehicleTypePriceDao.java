package com.basware.repositories;

import com.basware.models.taxes.Price;
import com.basware.models.taxes.VehiclePrice;
import com.basware.models.vehicles.VehicleType;

import java.util.Optional;

public interface VehicleTypePriceDao {
    Optional<Price> findByVehicleType(VehicleType vehicleType);
    void save(VehiclePrice vehiclePrice);
    void deleteAll();
}

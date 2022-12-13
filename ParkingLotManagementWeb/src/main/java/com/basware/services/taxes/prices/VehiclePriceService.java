package com.basware.services.taxes.prices;

import com.basware.models.taxes.Price;
import com.basware.models.vehicles.VehicleType;
import com.basware.exceptions.ResourceNotFoundException;

public interface VehiclePriceService {
    Price getPrice(VehicleType vehicleType) throws ResourceNotFoundException;
}

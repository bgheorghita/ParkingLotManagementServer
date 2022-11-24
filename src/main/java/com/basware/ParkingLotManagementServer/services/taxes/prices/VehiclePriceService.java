package com.basware.ParkingLotManagementServer.services.taxes.prices;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;

public interface VehiclePriceService {
    Price getPrice(VehicleType vehicleType) throws ResourceNotFoundException;
}

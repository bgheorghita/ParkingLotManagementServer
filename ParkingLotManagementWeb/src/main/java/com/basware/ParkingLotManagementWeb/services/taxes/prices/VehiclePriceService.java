package com.basware.ParkingLotManagementWeb.services.taxes.prices;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;

public interface VehiclePriceService {
    Price getPrice(VehicleType vehicleType) throws ResourceNotFoundException;
}

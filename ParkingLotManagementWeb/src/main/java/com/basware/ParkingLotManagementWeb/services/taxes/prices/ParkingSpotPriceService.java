package com.basware.ParkingLotManagementWeb.services.taxes.prices;

import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;

public interface ParkingSpotPriceService {
    Price getPrice(ParkingSpotType parkingSpotType) throws ResourceNotFoundException;
}

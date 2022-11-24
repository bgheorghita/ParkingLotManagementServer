package com.basware.ParkingLotManagementServer.services.taxes.prices;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Price;

public interface ParkingSpotPriceService {
    Price getPrice(ParkingSpotType parkingSpotType) throws ResourceNotFoundException;
}

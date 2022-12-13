package com.basware.services.taxes.prices;

import com.basware.models.parkings.spots.ParkingSpotType;
import com.basware.models.taxes.Price;
import com.basware.exceptions.ResourceNotFoundException;

public interface ParkingSpotPriceService {
    Price getPrice(ParkingSpotType parkingSpotType) throws ResourceNotFoundException;
}

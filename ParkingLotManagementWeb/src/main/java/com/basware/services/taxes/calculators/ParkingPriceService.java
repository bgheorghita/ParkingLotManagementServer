package com.basware.services.taxes.calculators;

import com.basware.models.parkings.spots.ParkingSpotType;
import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import com.basware.models.vehicles.VehicleType;
import com.basware.exceptions.ResourceNotFoundException;
import com.basware.exceptions.ServiceNotAvailable;

public interface ParkingPriceService {
    Price getParkingPrice(int parkingTimeInMinutes, UserType userType, VehicleType vehicleType,
                          ParkingSpotType parkingSpotType, Currency toCurrency) throws ResourceNotFoundException, ServiceNotAvailable;
}

package com.basware.ParkingLotManagementServer.services.taxes.calculators;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.exceptions.ServiceNotAvailable;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;

public interface ParkingPriceService {
    Price getParkingPrice(int parkingTimeInMinutes, UserType userType, VehicleType vehicleType,
                          ParkingSpotType parkingSpotType, Currency toCurrency) throws ResourceNotFoundException, ServiceNotAvailable;
}

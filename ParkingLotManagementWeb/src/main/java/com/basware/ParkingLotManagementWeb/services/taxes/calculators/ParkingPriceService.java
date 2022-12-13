package com.basware.ParkingLotManagementWeb.services.taxes.calculators;

import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;

public interface ParkingPriceService {
    Price getParkingPrice(int parkingTimeInMinutes, UserType userType, VehicleType vehicleType,
                          ParkingSpotType parkingSpotType, Currency toCurrency) throws ResourceNotFoundException, ServiceNotAvailable;
}

package com.basware.ParkingLotManagementWeb.services.taxes.calculators;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementWeb.exceptions.InvalidInput;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;

public interface ParkingPriceService {
    Price getParkingPrice(String parkingTimeInMinutes, String userType, String vehicleType,
                          String parkingSpotType, String toCurrency) throws ResourceNotFoundException, ServiceNotAvailable, InvalidInput;
}

package com.basware.ParkingLotManagementWeb.services.taxes.prices;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;

public interface UserPriceService {
    Price getPrice(UserType userType) throws ResourceNotFoundException;
}

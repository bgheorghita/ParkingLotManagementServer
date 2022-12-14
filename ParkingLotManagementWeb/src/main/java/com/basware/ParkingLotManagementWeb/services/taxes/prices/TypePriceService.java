package com.basware.ParkingLotManagementWeb.services.taxes.prices;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;

public interface TypePriceService {
    Price getPrice(TypeInfo typeInfo) throws ResourceNotFoundException;
}

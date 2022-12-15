package com.basware.ParkingLotManagementWeb.repositories.taxes;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementCommon.models.taxes.TypePrice;

import java.util.Optional;

public interface TypePriceDao {
    Optional<Price> getPriceByTypeInfo(TypeInfo typeInfo);
    boolean saveUnique(TypePrice typePrice);
    long deleteAll();
}

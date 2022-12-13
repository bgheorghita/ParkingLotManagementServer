package com.basware.ParkingLotManagementWeb.repositories.taxes;


import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.UserPrice;
import com.basware.ParkingLotManagementCommon.models.users.UserType;

import java.util.Optional;

public interface UserTypePriceDao {
    Optional<Price> findByUserType(UserType userType);
    void save(UserPrice userPrice);
    void deleteAll();
}

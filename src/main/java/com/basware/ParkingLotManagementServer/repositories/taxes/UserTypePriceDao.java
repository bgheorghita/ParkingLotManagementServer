package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.taxes.UserPrice;
import com.basware.ParkingLotManagementServer.models.users.UserType;

import java.util.Optional;

public interface UserTypePriceDao {
    Optional<Price> findByUserType(UserType userType);
    void save(UserPrice userPrice);
    void deleteAll();
}

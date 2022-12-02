package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.taxes.UserPrice;
import com.basware.ParkingLotManagementServer.models.users.UserType;

import java.util.Optional;

public interface UserTypePriceDao {
    Optional<UserPrice> findByUserType(UserType userType);
    void save(UserPrice userPrice);
    void deleteAll();
}

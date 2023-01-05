package com.basware.ParkingLotManagementWeb.repositories.taxes;

import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;

import java.util.Optional;

public interface UserTypeDiscountPercentDao {
    Optional<Double> findByUserType(UserType userType);
    boolean save(UserDiscount userPrice);
    long deleteAll();
}

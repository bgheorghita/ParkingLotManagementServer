package com.basware.ParkingLotManagementServer.repositories.taxes.discounts;

import com.basware.ParkingLotManagementServer.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserDiscountRepository extends MongoRepository<UserDiscount, String> {
    Optional<UserDiscount> findByUserType(UserType userType);
}

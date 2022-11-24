package com.basware.ParkingLotManagementServer.repositories.taxes;

import com.basware.ParkingLotManagementServer.models.taxes.UserPrice;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserPriceRepository extends MongoRepository<UserPrice, String> {
    Optional<UserPrice> findByUserType(UserType userType);
}

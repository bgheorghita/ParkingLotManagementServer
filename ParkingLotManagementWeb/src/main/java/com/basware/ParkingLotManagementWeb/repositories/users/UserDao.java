package com.basware.ParkingLotManagementWeb.repositories.users;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User> {
    List<User> findAllByUserType(UserType userType);
}

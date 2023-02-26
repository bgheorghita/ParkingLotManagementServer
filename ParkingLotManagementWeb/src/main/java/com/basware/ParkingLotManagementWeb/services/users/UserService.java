package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.services.CrudService;

import java.util.List;

public interface UserService extends CrudService<User> {
    List<User> findAllByUserType(UserType userType);
    User findFirstByVehiclePlateNumber(String vehiclePlateNumber) throws ResourceNotFoundException;
    User findFirstByUsername(String username) throws ResourceNotFoundException;
    List<User> findValidatedUsers();
    List<User> findUnvalidatedUsers();
}

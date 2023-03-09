package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.services.CrudService;

import java.util.List;

public interface UserService extends CrudService<User> {
    User findFirstByUsername(String username) throws ResourceNotFoundException;
    List<User> getNonAdminUsers();
    void validateUser(String username) throws TooManyRequestsException, SaveException, ResourceNotFoundException;
    void invalidateUser(String username) throws ResourceNotFoundException, TooManyRequestsException, SaveException;
}
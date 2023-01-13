package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.repositories.users.UserDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends CrudServiceImpl<User> implements UserService{

    public UserServiceImpl(UserDao userDao) {
        super(userDao);
    }

    @Override
    public List<User> findAllByUserType(UserType userType) {
        return ((UserDao) crudRepository).findAllByUserType(userType);
    }
}

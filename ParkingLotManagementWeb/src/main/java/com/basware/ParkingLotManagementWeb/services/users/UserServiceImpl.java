package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.users.UserDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.bson.BsonBoolean;
import org.bson.BsonString;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends CrudServiceImpl<User> implements UserService{

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAllByUserType(UserType userType) {
        return userDao.findAllByUserType(userType);
    }

    @Override
    public User findFirstByVehiclePlateNumber(String vehiclePlateNumber) throws ResourceNotFoundException {
        List<User> users = userDao.findByVehiclePlateNumber(vehiclePlateNumber);
        if(users.size() > 0){
            return users.get(0);
        }
        throw new ResourceNotFoundException(String.format("User by vehicle plate number \"%s\" could not be found.", vehiclePlateNumber));
    }

    @Override
    public User findFirstByUsername(String username) throws ResourceNotFoundException {
        List<User> users = userDao.findAllByFieldValues(Map.of(User.USERNAME_FIELD, new BsonString(username)));
        if(users.size() > 0){
            return users.get(0);
        }
        throw new ResourceNotFoundException(String.format("Username \"%s\" has not been found.", username));
    }

    @Override
    public List<User> findUnvalidatedUsers() {
        return userDao.findAllByFieldValues(Map.of(User.IS_VALIDATED_FIELD, new BsonBoolean(false)));
    }

    @Override
    public List<User> findValidatedUsers() {
        return userDao.findAllByFieldValues(Map.of(User.IS_VALIDATED_FIELD, new BsonBoolean(true)));
    }
}

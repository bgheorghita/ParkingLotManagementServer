package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.users.Role;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.repositories.users.UserDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.bson.BsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends CrudServiceImpl<User> implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User findFirstByUsername(String username) throws ResourceNotFoundException {
        List<User> users = userDao.findAllByFieldValues(Map.of(User.USERNAME_FIELD, new BsonString(username)));
        if(users.size() > 0){
            return users.get(0);
        }
        throw new ResourceNotFoundException(String.format("Username \"%s\" has not been found.", username));
    }

    @Override
    public List<User> getNonAdminUsers() {
        return this.findAllByNotMatchValue(User.USER_ROLES_FIELD, Role.ADMIN.name());
    }

    @Override
    public void validateUser(String username) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        User user = this.findFirstByUsername(username);
        user.setValidated(true);
        this.save(user);
    }

    @Override
    public void invalidateUser(String username) throws ResourceNotFoundException, TooManyRequestsException, SaveException {
        User user = this.findFirstByUsername(username);
        user.setValidated(false);
        this.save(user);
    }
}
package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final CrudRepository<User> userCrudRepository;

    public UserServiceImpl(CrudRepository<User> userCrudRepository){
        this.userCrudRepository = userCrudRepository;
    }
    @Override
    public boolean save(User user) {
        return userCrudRepository.save(user);
    }
}

package com.basware.ParkingLotManagementWeb.repositories.users;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepositoryImpl;
import dev.morphia.Datastore;
import org.bson.BsonString;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl extends CrudRepositoryImpl<User> implements UserDao {

    public UserDaoImpl(Datastore datastore) {
        super(User.class, datastore);
    }

    @Override
    public List<User> findAllByUserType(UserType userType) {
        return findAllByFieldValues(Map.of(User.USER_TYPE_FIELD, new BsonString(userType.name())));
    }

    @Override
    public List<User> findByVehiclePlateNumber(String vehiclePlateNumber) {
        return findAllByFieldValues(Map.of(User.VEHICLE_PLATE_NUMBERS_FIELD, new BsonString(vehiclePlateNumber)));
    }
}

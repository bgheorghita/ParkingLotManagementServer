package com.basware.ParkingLotManagementWeb.api.v1.mappers;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;

import java.util.Set;

public class UserMapper {
    public static UserDto fromUserToUserDto(User user){
        UserType userType = user.getUserType();
        Set<String> vehiclePlateNumbers = user.getVehiclePlateNumbers();
        String username = user.getUsername();
        boolean isValidated = user.getIsValidated();

        return new UserDto(userType, vehiclePlateNumbers, username, isValidated);
    }
}

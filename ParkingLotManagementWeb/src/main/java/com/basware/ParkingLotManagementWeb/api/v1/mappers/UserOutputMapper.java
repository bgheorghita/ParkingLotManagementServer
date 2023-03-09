package com.basware.ParkingLotManagementWeb.api.v1.mappers;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserOutputMapper {
    public UserDto fromUserToUserDto(User user){
        UserType userType = user.getUserType();
        Set<String> vehiclePlateNumbers = user.getVehiclePlateNumbers();
        String username = user.getUsername();
        boolean isValidated = user.getIsValidated();

        return new UserDto(userType, vehiclePlateNumbers, username, isValidated);
    }
}

package com.basware.ParkingLotManagementWeb.api.v1.models;

import com.basware.ParkingLotManagementCommon.models.users.UserType;
import lombok.Builder;

import java.util.Set;

@Builder
public class UserDto {
    private final UserType userType;
    private final Set<String> vehiclePlateNumbers;
    private final String username;
    private final boolean isValidated;

    public UserDto(UserType userType, Set<String> vehiclePlateNumbers, String username, boolean isValidated) {
        this.userType = userType;
        this.vehiclePlateNumbers = vehiclePlateNumbers;
        this.username = username;
        this.isValidated = isValidated;
    }

    public UserType getUserType() {
        return userType;
    }

    public Set<String> getVehiclePlateNumbers() {
        return vehiclePlateNumbers;
    }

    public String getUsername() {
        return username;
    }

    public boolean getIsValidated() {
        return isValidated;
    }
}

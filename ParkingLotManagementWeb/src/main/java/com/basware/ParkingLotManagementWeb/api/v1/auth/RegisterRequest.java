package com.basware.ParkingLotManagementWeb.api.v1.auth;
import com.basware.ParkingLotManagementCommon.models.users.UserType;

public class RegisterRequest {
    private String username;
    private UserType userType;
    private String password;

    public RegisterRequest(String username, UserType userType, String password) {
        this.username = username;
        this.userType = userType;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

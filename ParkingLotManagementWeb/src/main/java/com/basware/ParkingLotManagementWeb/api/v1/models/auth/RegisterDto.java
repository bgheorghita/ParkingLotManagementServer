package com.basware.ParkingLotManagementWeb.api.v1.models.auth;
import com.basware.ParkingLotManagementCommon.models.users.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterDto {
    @Pattern(regexp="[a-zA-Z]{4,10}", message = "The username length must be in range of 4-10 letters only.")
    @NotNull(message = "Username must not be null.")
    private String username;

    @NotNull(message = "UserType must not be null.")
    @Pattern(regexp = "REGULAR|VIP", message = "UserType must be REGULAR or VIP.")
    private String userType;
    @Size(min=4, message = "The password length must be minimum length of 4.")
    @NotNull(message = "The password must not be null.")
    @Pattern(regexp = "^\\S+$", message = "The password must not contain spaces.")
    private String password;

    public RegisterDto(String username, String userType, String password) {
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
        return UserType.valueOf(userType);
    }

    public void setUserType(UserType userType) {
        this.userType = userType.name();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
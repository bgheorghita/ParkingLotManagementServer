package com.basware.ParkingLotManagementWeb.api.v1.auth;

import com.basware.ParkingLotManagementCommon.models.users.Role;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String username;
    private UserType userType;
    private Set<Role> roles;
    private String token;
    private String tokenExpirationDateInMillis;
}
package com.basware.ParkingLotManagementServer.models.taxes;

import com.basware.ParkingLotManagementServer.models.users.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPrice {
    private UserType userType;
    private Price price;
}
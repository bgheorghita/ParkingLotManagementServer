package com.basware.ParkingLotManagementCommon.models.taxes;

import com.basware.ParkingLotManagementCommon.models.users.UserType;
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
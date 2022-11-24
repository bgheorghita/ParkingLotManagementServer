package com.basware.ParkingLotManagementServer.models.taxes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Price {
    private double units;
    private Currency currency;
}

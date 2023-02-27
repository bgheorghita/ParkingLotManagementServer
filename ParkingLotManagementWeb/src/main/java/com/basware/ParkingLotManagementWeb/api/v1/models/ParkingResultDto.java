package com.basware.ParkingLotManagementWeb.api.v1.models;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;

public class ParkingResultDto {
    private final long parkingTimeInMinutes;
    private final Price price;

    public ParkingResultDto(long parkingTimeInMinutes, Price price){
        this.parkingTimeInMinutes = parkingTimeInMinutes;
        this.price = price;
    }

    public long getParkingTimeInMinutes(){
        return parkingTimeInMinutes;
    }

    public Price getPrice() {
        return price;
    }
}

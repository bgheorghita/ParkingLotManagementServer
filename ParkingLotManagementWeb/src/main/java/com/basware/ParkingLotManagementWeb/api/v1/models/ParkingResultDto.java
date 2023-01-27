package com.basware.ParkingLotManagementWeb.api.v1.models;

public class ParkingResultDto {
    final long parkingTimeInMinutes;

    public ParkingResultDto(long parkingTimeInMinutes){
        this.parkingTimeInMinutes = parkingTimeInMinutes;
    }

    public long getParkingTimeInMinutes(){
        return parkingTimeInMinutes;
    }
}

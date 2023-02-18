package com.basware.ParkingLotManagementWeb.api.v1.models;

public class ParkingInputDto {
    private final String username;
    private final String vehiclePlateNumber;

    public ParkingInputDto(String username, String vehiclePlateNumber) {
        this.username = username;
        this.vehiclePlateNumber = vehiclePlateNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }
}

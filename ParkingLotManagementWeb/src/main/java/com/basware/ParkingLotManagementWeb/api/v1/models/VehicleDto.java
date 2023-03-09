package com.basware.ParkingLotManagementWeb.api.v1.models;

import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;

public class VehicleDto {
    private final VehicleType vehicleType;
    private final String plateNumber;
    private final boolean isElectric;
    private final boolean isParked;


    public VehicleDto(VehicleType vehicleType, String plateNumber, boolean isElectric, boolean isParked) {
        this.vehicleType = vehicleType;
        this.plateNumber = plateNumber;
        this.isElectric = isElectric;
        this.isParked = isParked;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public boolean getIsElectric() {
        return isElectric;
    }

    public boolean getIsParked() {
        return isParked;
    }

    @Override
    public String toString() {
        return "VehicleDto{" +
                "vehicleType=" + vehicleType +
                ", plateNumber='" + plateNumber + '\'' +
                ", isElectric=" + isElectric +
                ", isParked=" + isParked +
                '}';
    }
}

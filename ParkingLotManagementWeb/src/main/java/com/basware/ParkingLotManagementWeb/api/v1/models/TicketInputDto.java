package com.basware.ParkingLotManagementWeb.api.v1.models;

import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;

public class TicketInputDto {
    private final String userName;
    private final UserType userType;
    private final String vehiclePlateNumber;
    private final VehicleType vehicleType;
    private final boolean electricVehicle;


    public TicketInputDto(String userName, UserType userType, String vehiclePlateNumber, VehicleType vehicleType, boolean electricVehicle) {
        this.userName = userName;
        this.userType = userType;
        this.vehiclePlateNumber = vehiclePlateNumber;
        this.vehicleType = vehicleType;
        this.electricVehicle = electricVehicle;
    }

    public String getUserName() {
        return userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean vehicleIsElectric() {
        return electricVehicle;
    }
}

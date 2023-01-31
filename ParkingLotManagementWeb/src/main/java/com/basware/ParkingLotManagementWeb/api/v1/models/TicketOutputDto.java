package com.basware.ParkingLotManagementWeb.api.v1.models;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;

import java.time.LocalDateTime;

public class TicketOutputDto {
    private String userName;
    private UserType userType;
    private String vehiclePlateNumber;
    private VehicleType vehicleType;
    private boolean electricVehicle;
    private ParkingSpotType parkingSpotType;
    private boolean parkingSpotWithElectricCharger;
    private Long parkingSpotNumber;
    private LocalDateTime time;

    private TicketOutputDto(String userName, UserType userType, String vehiclePlateNumber, VehicleType vehicleType, boolean electricVehicle, ParkingSpotType parkingSpotType, boolean parkingSpotWithElectricCharger, Long parkingSpotNumber, LocalDateTime time) {
        this.userName = userName;
        this.userType = userType;
        this.vehiclePlateNumber = vehiclePlateNumber;
        this.vehicleType = vehicleType;
        this.electricVehicle = electricVehicle;
        this.parkingSpotType = parkingSpotType;
        this.parkingSpotWithElectricCharger = parkingSpotWithElectricCharger;
        this.parkingSpotNumber = parkingSpotNumber;
        this.time = time;
    }

    public TicketOutputDto(){}

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

    public boolean isElectricVehicle() {
        return electricVehicle;
    }

    public ParkingSpotType getParkingSpotType() {
        return parkingSpotType;
    }

    public boolean isParkingSpotWithElectricCharger() {
        return parkingSpotWithElectricCharger;
    }

    public Long getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public TicketOutputDto withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public TicketOutputDto withUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public TicketOutputDto withVehiclePlateNumber(String vehiclePlateNumber) {
        this.vehiclePlateNumber = vehiclePlateNumber;
        return this;
    }

    public TicketOutputDto withVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public TicketOutputDto withElectricVehicle(boolean electricVehicle) {
        this.electricVehicle = electricVehicle;
        return this;
    }

    public TicketOutputDto withParkingSpotType(ParkingSpotType parkingSpotType) {
        this.parkingSpotType = parkingSpotType;
        return this;
    }

    public TicketOutputDto withParkingSpotWithElectricCharger(boolean parkingSpotWithElectricCharger) {
        this.parkingSpotWithElectricCharger = parkingSpotWithElectricCharger;
        return this;
    }

    public TicketOutputDto withParkingSpotNumber(Long parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
        return this;
    }

    public TicketOutputDto withTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public TicketOutputDto build() {
        return new TicketOutputDto(userName, userType, vehiclePlateNumber, vehicleType, electricVehicle, parkingSpotType, parkingSpotWithElectricCharger, parkingSpotNumber, time);
    }
}

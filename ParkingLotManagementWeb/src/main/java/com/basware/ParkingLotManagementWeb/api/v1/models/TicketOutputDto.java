package com.basware.ParkingLotManagementWeb.api.v1.models;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;

import java.time.LocalDateTime;

public class TicketOutputDto {
    private String ticketObjectId;
    private String userName;
    private UserType userType;
    private String vehiclePlateNumber;
    private VehicleType vehicleType;
    private boolean electricVehicle;
    private ParkingSpotType parkingSpotType;
    private boolean parkingSpotWithElectricCharger;
    private Long parkingSpotNumber;
    private LocalDateTime time;

    public TicketOutputDto(String ticketObjectId, String userName, UserType userType, String vehiclePlateNumber, VehicleType vehicleType, boolean electricVehicle, ParkingSpotType parkingSpotType, boolean parkingSpotWithElectricCharger, Long parkingSpotNumber, LocalDateTime time) {
        this.ticketObjectId = ticketObjectId;
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

    public String getTicketObjectId(){return ticketObjectId;}

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

    public TicketOutputDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public TicketOutputDto setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public TicketOutputDto setVehiclePlateNumber(String vehiclePlateNumber) {
        this.vehiclePlateNumber = vehiclePlateNumber;
        return this;
    }

    public TicketOutputDto setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public TicketOutputDto setElectricVehicle(boolean electricVehicle) {
        this.electricVehicle = electricVehicle;
        return this;
    }

    public TicketOutputDto setParkingSpotType(ParkingSpotType parkingSpotType) {
        this.parkingSpotType = parkingSpotType;
        return this;
    }

    public TicketOutputDto setParkingSpotWithElectricCharger(boolean parkingSpotWithElectricCharger) {
        this.parkingSpotWithElectricCharger = parkingSpotWithElectricCharger;
        return this;
    }

    public TicketOutputDto setParkingSpotNumber(Long parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
        return this;
    }

    public TicketOutputDto setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public TicketOutputDto setTicketObjectId(String ticketObjectId) {
        this.ticketObjectId = ticketObjectId;
        return this;
    }

    public TicketOutputDto build() {
        return new TicketOutputDto(ticketObjectId, userName, userType, vehiclePlateNumber, vehicleType, electricVehicle, parkingSpotType, parkingSpotWithElectricCharger, parkingSpotNumber, time);
    }
}

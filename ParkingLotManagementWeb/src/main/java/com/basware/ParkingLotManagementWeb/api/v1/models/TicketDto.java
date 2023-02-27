package com.basware.ParkingLotManagementWeb.api.v1.models;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import lombok.Builder;

@Builder
public class TicketDto {
    private final String vehiclePlateNumber;
    private final long parkingSpotNumber;
    private final ParkingSpotType parkingSpotType;
    private final long timestampParkAt;

    public TicketDto(String vehiclePlateNumber, long parkingSpotNumber, ParkingSpotType parkingSpotType, long timestampParkAt) {
        this.vehiclePlateNumber = vehiclePlateNumber;
        this.parkingSpotNumber = parkingSpotNumber;
        this.parkingSpotType = parkingSpotType;
        this.timestampParkAt = timestampParkAt;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public long getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public ParkingSpotType getParkingSpotType() {
        return parkingSpotType;
    }

    public long getTimestampParkAt() {
        return timestampParkAt;
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
                ", parkingSpotNumber=" + parkingSpotNumber +
                ", parkingSpotType=" + parkingSpotType +
                ", timestampParkAt=" + timestampParkAt +
                '}';
    }
}

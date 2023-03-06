package com.basware.ParkingLotManagementWeb.api.v1.models;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import lombok.Builder;

@Builder
public class TicketDto {
    private final String vehiclePlateNumber;
    private final boolean electricVehicle;
    private final long parkingSpotNumber;
    private final ParkingSpotType parkingSpotType;
    private final boolean parkingSpotWithElectricCharger;
    private final long timestampParkAt;

    public TicketDto(String vehiclePlateNumber, boolean electricVehicle, long parkingSpotNumber,
                     ParkingSpotType parkingSpotType, boolean parkingSpotWithElectricCharger, long timestampParkAt) {
        this.vehiclePlateNumber = vehiclePlateNumber;
        this.electricVehicle = electricVehicle;
        this.parkingSpotNumber = parkingSpotNumber;
        this.parkingSpotType = parkingSpotType;
        this.parkingSpotWithElectricCharger = parkingSpotWithElectricCharger;
        this.timestampParkAt = timestampParkAt;
    }

    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    public boolean getIsElectricVehicle() {
        return electricVehicle;
    }

    public long getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public ParkingSpotType getParkingSpotType() {
        return parkingSpotType;
    }

    public boolean getIsParkingSpotWithElectricCharger() {
        return parkingSpotWithElectricCharger;
    }

    public long getTimestampParkAt() {
        return timestampParkAt;
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
                ", electricVehicle=" + electricVehicle +
                ", parkingSpotNumber=" + parkingSpotNumber +
                ", parkingSpotType=" + parkingSpotType +
                ", parkingSpotWithElectricCharger=" + parkingSpotWithElectricCharger +
                ", timestampParkAt=" + timestampParkAt +
                '}';
    }
}

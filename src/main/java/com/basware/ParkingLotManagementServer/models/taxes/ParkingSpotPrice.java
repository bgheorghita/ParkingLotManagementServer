package com.basware.ParkingLotManagementServer.models.taxes;

import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;

public class ParkingSpotPrice {
    private ParkingSpotType parkingSpotType;
    private Price price;

    public ParkingSpotPrice(ParkingSpotType parkingSpotType, Price price) {
        this.parkingSpotType = parkingSpotType;
        this.price = price;
    }

    public ParkingSpotType getParkingSpotType() {
        return parkingSpotType;
    }

    public void setParkingSpotType(ParkingSpotType parkingSpotType) {
        this.parkingSpotType = parkingSpotType;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}


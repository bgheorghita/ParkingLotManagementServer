package com.basware.ParkingLotManagementWeb.exceptions;

public class VehicleAlreadyParkedException extends ConflictException {
    public VehicleAlreadyParkedException(String msg) {
        super(msg);
    }
}

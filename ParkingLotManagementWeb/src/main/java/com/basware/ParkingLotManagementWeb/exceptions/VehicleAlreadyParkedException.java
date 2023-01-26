package com.basware.ParkingLotManagementWeb.exceptions;

public class VehicleAlreadyParkedException extends TicketException {
    public VehicleAlreadyParkedException(String msg) {
        super(msg);
    }
}

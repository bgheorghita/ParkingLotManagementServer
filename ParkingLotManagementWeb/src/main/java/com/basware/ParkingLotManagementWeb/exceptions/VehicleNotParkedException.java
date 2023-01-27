package com.basware.ParkingLotManagementWeb.exceptions;

public class VehicleNotParkedException extends TicketException{
    public VehicleNotParkedException(String msg) {
        super(msg);
    }
}

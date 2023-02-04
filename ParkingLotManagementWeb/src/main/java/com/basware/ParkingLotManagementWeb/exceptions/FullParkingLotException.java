package com.basware.ParkingLotManagementWeb.exceptions;

public class FullParkingLotException extends ResourceNotFoundException{
    public FullParkingLotException(String msg) {
        super(msg);
    }
}

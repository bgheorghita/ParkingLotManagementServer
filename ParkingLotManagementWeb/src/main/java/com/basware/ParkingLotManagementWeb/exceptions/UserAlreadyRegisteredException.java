package com.basware.ParkingLotManagementWeb.exceptions;

public class UserAlreadyRegisteredException extends ConflictException{
    public UserAlreadyRegisteredException(String msg){
        super(msg);
    }
}

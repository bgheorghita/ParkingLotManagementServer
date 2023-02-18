package com.basware.ParkingLotManagementWeb.exceptions;

public class UserAlreadyRegisteredException extends Exception{
    public UserAlreadyRegisteredException(String msg){
        super(msg);
    }
}

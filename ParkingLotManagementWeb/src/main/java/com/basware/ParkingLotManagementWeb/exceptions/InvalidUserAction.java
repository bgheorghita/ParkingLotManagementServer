package com.basware.ParkingLotManagementWeb.exceptions;

public class InvalidUserAction extends BadRequestException{
    public InvalidUserAction(String msg){
        super(msg);
    }
}

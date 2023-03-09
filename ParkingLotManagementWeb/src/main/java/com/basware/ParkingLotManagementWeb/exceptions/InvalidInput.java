package com.basware.ParkingLotManagementWeb.exceptions;

public class InvalidInput extends BadRequestException{
    public InvalidInput(String msg){
        super(msg);
    }

}

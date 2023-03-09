package com.basware.ParkingLotManagementWeb.exceptions;

public class SaveException extends InternalServerErrorException{
    public SaveException(String msg){
        super(msg);
    }
}

package com.basware.ParkingLotManagementWeb.exceptions;

public class TicketException extends InternalServerErrorException{
    public TicketException(String msg){
        super(msg);
    }
}

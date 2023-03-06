package com.basware.ParkingLotManagementWeb.services.parking.lots;

import com.basware.ParkingLotManagementWeb.api.v1.models.ParkingResultDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;

public interface ParkingLotService {
    TicketOutputDto generateTicket(String username, String vehiclePlateNumber) throws TicketException, SaveException, ServiceNotAvailable, TooManyRequestsException, ResourceNotFoundException, UnauthorizedException;
    ParkingResultDto leaveParkingLot(String username, String vehiclePlateNumber) throws ResourceNotFoundException, TicketException, ServiceNotAvailable, TooManyRequestsException, SaveException;
}

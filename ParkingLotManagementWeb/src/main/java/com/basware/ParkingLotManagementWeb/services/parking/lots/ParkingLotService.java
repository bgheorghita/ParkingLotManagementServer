package com.basware.ParkingLotManagementWeb.services.parking.lots;

import com.basware.ParkingLotManagementWeb.api.v1.models.ParkingResultDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;

public interface ParkingLotService {
    TicketOutputDto generateTicket(String username, String vehiclePlateNumber) throws UnauthorizedException, ResourceNotFoundException, VehicleAlreadyParkedException, TooManyRequestsException, SaveException;
    ParkingResultDto leaveParkingLot(String username, String vehiclePlateNumber) throws ResourceNotFoundException, VehicleNotParkedException, TooManyRequestsException, SaveException, ServiceNotAvailable;
}

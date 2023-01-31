package com.basware.ParkingLotManagementWeb.services.parking.lots;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.models.ParkingResultDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;

public interface ParkingLotService {
    TicketOutputDto generateTicket(User user, Vehicle vehicle) throws TicketException, SaveException, ServiceNotAvailable, TooManyRequestsException, ResourceNotFoundException;
    ParkingResultDto leaveParkingLot(String vehiclePlateNumber) throws ResourceNotFoundException, TicketException, ServiceNotAvailable, TooManyRequestsException, SaveException;
}

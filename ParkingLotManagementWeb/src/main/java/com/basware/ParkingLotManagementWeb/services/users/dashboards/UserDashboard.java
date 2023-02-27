package com.basware.ParkingLotManagementWeb.services.users.dashboards;

import com.basware.ParkingLotManagementWeb.api.v1.models.TicketDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;

import java.util.List;

public interface UserDashboard {
    void addVehicle(String username, VehicleDto vehicleDto) throws ResourceNotFoundException, TooManyRequestsException, SaveException;
    void removeVehicle(String username, String vehiclePlateNumber) throws ResourceNotFoundException, TooManyRequestsException, SaveException, InvalidUserAction;
    List<VehicleDto> findAllVehicles(String username) throws ResourceNotFoundException;
    List<TicketDto> findAllTickets(String username) throws ResourceNotFoundException;
    UserDto getAccountDetails(String username) throws ResourceNotFoundException;
}

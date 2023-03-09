package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutput;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;

import java.util.List;

public interface UserAccountService{
    void addVehicleToUserAccount(String username, VehicleDto vehicleDto) throws ResourceNotFoundException, TooManyRequestsException, SaveException;
    void removeUnparkedVehicleFromUserAccount(String username, String vehiclePlateNumber) throws ResourceNotFoundException, TooManyRequestsException, SaveException, ConflictException;
    List<TicketOutput> findTicketsFromUserAccount(String username) throws ResourceNotFoundException;
    List<Vehicle> findVehiclesFromUserAccount(String username) throws ResourceNotFoundException;
}
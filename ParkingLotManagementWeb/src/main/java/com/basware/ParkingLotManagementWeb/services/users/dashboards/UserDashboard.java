package com.basware.ParkingLotManagementWeb.services.users.dashboards;

import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;

import java.util.List;

public interface UserDashboard {
    void addVehicle(String username, VehicleDto vehicleDto) throws ResourceNotFoundException, TooManyRequestsException, SaveException;
    void removeVehicle(String username, String vehiclePlateNumber) throws ResourceNotFoundException, TooManyRequestsException, SaveException;
    List<VehicleDto> findAllVehicles(String username) throws ResourceNotFoundException;
}

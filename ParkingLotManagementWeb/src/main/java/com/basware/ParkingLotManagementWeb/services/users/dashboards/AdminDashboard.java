package com.basware.ParkingLotManagementWeb.services.users.dashboards;

import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;

import java.util.List;

public interface AdminDashboard {
    List<UserDto> getUnvalidatedAccounts();
    List<UserDto> getValidatedAccounts();
    List<VehicleDto> getParkedVehicles();
    void validateUserAccount(String username) throws TooManyRequestsException, SaveException, ResourceNotFoundException;
    void invalidateUserAccount(String username) throws ResourceNotFoundException, TooManyRequestsException, SaveException;
    List<UserDto> getAllUsers();
}

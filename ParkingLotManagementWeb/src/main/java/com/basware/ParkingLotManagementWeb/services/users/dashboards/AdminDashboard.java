package com.basware.ParkingLotManagementWeb.services.users.dashboards;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;

import java.util.List;

public interface AdminDashboard {
    List<User> getUnvalidatedAccounts();
    List<User> getValidatedAccounts();
    List<Vehicle> getParkedVehicles();
    void validateUserAccount(User user) throws TooManyRequestsException, SaveException;
}

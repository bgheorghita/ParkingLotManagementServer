package com.basware.ParkingLotManagementWeb.services.users.dashboards;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardImpl implements AdminDashboard{

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public List<User> getUnvalidatedAccounts() {
        return userService.findUnvalidatedUsers();
    }

    @Override
    public List<User> getValidatedAccounts() {
        return userService.findValidatedUsers();
    }

    @Override
    public List<Vehicle> getParkedVehicles() {
        return vehicleService.findParkedVehicles();
    }

    @Override
    public void validateUserAccount(User user) throws TooManyRequestsException, SaveException {
        user.setValidated(true);
        userService.save(user);
    }
}

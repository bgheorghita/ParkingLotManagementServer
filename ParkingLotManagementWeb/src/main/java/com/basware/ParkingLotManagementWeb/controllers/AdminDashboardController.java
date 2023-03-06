package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.services.users.dashboards.AdminDashboard;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AdminDashboardController.URL_BASE)
public class AdminDashboardController {
    public static final String URL_BASE = "api/v1/dashboard/admin";
    private final AdminDashboard adminDashboard;

    public AdminDashboardController(AdminDashboard adminDashboard){
        this.adminDashboard = adminDashboard;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll(){
        return adminDashboard.getAllUsers();
    }

    @GetMapping("/users/unvalidated")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUnvalidatedAccounts(){
        return adminDashboard.getUnvalidatedAccounts();
    }

    @GetMapping("/users/validated")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getValidatedAccounts(){
        return adminDashboard.getValidatedAccounts();
    }

    @GetMapping("/vehicles/parked")
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleDto> getParkedVehicles(){
        return adminDashboard.getParkedVehicles();
    }

    @PostMapping("users/validate/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void validateAccount(@PathVariable String username) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        adminDashboard.validateUserAccount(username);
    }

    @PostMapping("users/invalidate/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void invalidateAccount(@PathVariable String username) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        adminDashboard.invalidateUserAccount(username);
    }
}

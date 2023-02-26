package com.basware.ParkingLotManagementWeb.controllers;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.services.users.dashboards.UserDashboard;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(UserDashboardController.URL_BASE)
public class UserDashboardController {
    public static final String URL_BASE = "api/v1/profile";

    private final UserDashboard userDashboard;

    public UserDashboardController(UserDashboard userDashboard) {
        this.userDashboard = userDashboard;
    }

    @PostMapping("/vehicle")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveVehicle(@RequestBody VehicleDto vehicleDto, Principal principal) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        System.out.println(vehicleDto);
        String username = principal.getName();
        userDashboard.addVehicle(username, vehicleDto);
    }

    @DeleteMapping("/vehicle/{vehiclePlateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void removeVehicle(@PathVariable String vehiclePlateNumber, Principal principal) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        String username = principal.getName();
        userDashboard.removeVehicle(username, vehiclePlateNumber);
    }

    @GetMapping("/vehicle")
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleDto> findAllVehicles(Principal principal) throws ResourceNotFoundException {
        return userDashboard.findAllVehicles(principal.getName());
    }
}

package com.basware.ParkingLotManagementWeb.controllers;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementWeb.api.v1.mappers.UserMapper;
import com.basware.ParkingLotManagementWeb.api.v1.mappers.VehicleMapper;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutput;
import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.services.users.UserAccountService;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(UserDashboardController.URL_BASE)
public class UserDashboardController {
    public static final String URL_BASE = "api/v1/dashboard/user";

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserService userService;

    @PostMapping("/vehicle")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveVehicle(@RequestBody VehicleDto vehicleDto, Principal principal) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        System.out.println(vehicleDto);
        String username = principal.getName();
        userAccountService.addVehicleToUserAccount(username, vehicleDto);
    }

    @DeleteMapping("/vehicle/{vehiclePlateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void removeUnparkedVehicle(@PathVariable String vehiclePlateNumber, Principal principal) throws TooManyRequestsException, SaveException, ConflictException, ResourceNotFoundException {
        String username = principal.getName();
        userAccountService.removeUnparkedVehicleFromUserAccount(username, vehiclePlateNumber);
    }

    @GetMapping("/vehicle")
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleDto> findAllVehicles(Principal principal) throws ResourceNotFoundException {
        return userAccountService.findVehiclesFromUserAccount(principal.getName())
                .stream()
                .map(VehicleMapper::fromVehicleToVehicleDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/tickets")
    @ResponseStatus(HttpStatus.OK)
    public List<TicketOutput> findAllTickets(Principal principal) throws ResourceNotFoundException {
        return userAccountService.findTicketsFromUserAccount(principal.getName());
    }

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getAccountDetails(Principal principal) throws ResourceNotFoundException {
        User user = userService.findFirstByUsername(principal.getName());
        return UserMapper.fromUserToUserDto(user);
    }
}
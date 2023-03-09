package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementWeb.api.v1.mappers.UserOutputMapper;
import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AdminDashboardController.URL_BASE)
public class AdminDashboardController {
    public static final String URL_BASE = "api/v1/dashboard/admin";

    @Autowired
    private UserService userService;

    @Autowired
    private UserOutputMapper userOutputMapper;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll(){
        return userService.getNonAdminUsers()
                .stream()
                .map(userOutputMapper::fromUserToUserDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @PostMapping("users/validate/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void validateAccount(@PathVariable String username) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        userService.validateUser(username);
    }

    @PostMapping("users/invalidate/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void invalidateAccount(@PathVariable String username) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        userService.invalidateUser(username);
    }
}

package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.api.v1.models.auth.LoginDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.auth.RegisterDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.exceptions.UserAlreadyRegisteredException;
import com.basware.ParkingLotManagementWeb.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationService authenticateService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(@Valid @RequestBody RegisterDto registerDto) throws TooManyRequestsException, SaveException, UserAlreadyRegisteredException {
        String username = registerDto.getUsername();
        UserType userType = registerDto.getUserType();
        String password = registerDto.getPassword();
        return authenticateService.register(username, userType, password);
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginDto loginDto) throws ResourceNotFoundException {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        return authenticateService.authenticate(username, password);
    }
}

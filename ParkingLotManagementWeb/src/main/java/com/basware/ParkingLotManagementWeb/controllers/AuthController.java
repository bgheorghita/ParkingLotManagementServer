package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementWeb.api.v1.auth.AuthenticationRequest;
import com.basware.ParkingLotManagementWeb.api.v1.auth.AuthenticationResponse;
import com.basware.ParkingLotManagementWeb.api.v1.auth.RegisterRequest;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.exceptions.UserAlreadyRegisteredException;
import com.basware.ParkingLotManagementWeb.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws TooManyRequestsException, SaveException, UserAlreadyRegisteredException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.authenticate(request));
    }
}

package com.basware.ParkingLotManagementWeb.services.auth;

import com.basware.ParkingLotManagementCommon.models.users.Role;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementWeb.api.v1.auth.AuthenticationRequest;
import com.basware.ParkingLotManagementWeb.api.v1.auth.AuthenticationResponse;
import com.basware.ParkingLotManagementWeb.api.v1.auth.RegisterRequest;
import com.basware.ParkingLotManagementWeb.configs.JwtService;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.exceptions.UserAlreadyRegisteredException;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) throws TooManyRequestsException, SaveException, UserAlreadyRegisteredException {
        Set<Role> userRoles = Set.of(Role.REGULAR);
        User user = new User(request.getUsername(), userRoles, request.getUserType(), passwordEncoder.encode(request.getPassword()));
        checkIfAlreadyRegistered(request.getUsername());
        userService.save(user);
        Date tokenExpirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        var jwtToken = jwtService.generateToken(user, tokenExpirationDate);
        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .userType(user.getUserType())
                .roles(userRoles)
                .token(jwtToken)
                .tokenExpirationDateInMillis(String.valueOf(tokenExpirationDate.getTime()))
                .build();
    }

    private void checkIfAlreadyRegistered(String username) throws UserAlreadyRegisteredException {
        try {
            userService.findFirstByUsername(username);
            throw new UserAlreadyRegisteredException("Username \"" + username + "\" is already registered.");
        } catch (ResourceNotFoundException ignored) {}
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ResourceNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );
        User user = userService.findFirstByUsername(request.getUsername());
        Date tokenExpirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        var jwtToken = jwtService.generateToken(user, tokenExpirationDate);
        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .userType(user.getUserType())
                .roles(user.getRoles())
                .token(jwtToken)
                .tokenExpirationDateInMillis(String.valueOf(tokenExpirationDate.getTime()))
                .build();
    }
}

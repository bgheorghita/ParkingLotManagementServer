package com.basware.ParkingLotManagementWeb.services.auth;

import com.basware.ParkingLotManagementCommon.models.users.Role;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.exceptions.UserAlreadyRegisteredException;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

import static com.basware.ParkingLotManagementWeb.utils.Constants.ONE_HOUR_IN_MILLIS;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(String username, UserType userType, String password) throws UserAlreadyRegisteredException, TooManyRequestsException, SaveException {
        checkIfAlreadyRegistered(username);

        Role userRole = userType.equals(UserType.VIP) ? Role.VIP : Role.REGULAR;
        boolean validatedAccount = userRole.equals(Role.REGULAR);

        String encryptedPassword = passwordEncoder.encode(password);
        User user = new User(username, Set.of(userRole), userType, encryptedPassword, validatedAccount);

        userService.save(user);
        return generateToken(user);
    }

    private void checkIfAlreadyRegistered(String username) throws UserAlreadyRegisteredException {
        try {
            userService.findFirstByUsername(username);
            throw new UserAlreadyRegisteredException("Username \"" + username + "\" is already registered.");
        } catch (ResourceNotFoundException ignored) {}
    }

    public String authenticate(String username, String password) throws ResourceNotFoundException{
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(token);

        User user = userService.findFirstByUsername(username);
        return generateToken(user);
    }

    private String generateToken(User user){
        Date tokenExpirationDate = new Date(System.currentTimeMillis() + ONE_HOUR_IN_MILLIS);
        return jwtService.generateToken(user, tokenExpirationDate);
    }
}
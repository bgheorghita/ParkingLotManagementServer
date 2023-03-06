package com.basware.ParkingLotManagementWeb.services.users.dashboards;

import com.basware.ParkingLotManagementCommon.models.users.Role;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminDashboardImpl implements AdminDashboard{

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public List<UserDto> getUnvalidatedAccounts() {
        return userService.findUnvalidatedUsers()
                .stream()
                .map(this::getUserDtoFromUser)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<UserDto> getValidatedAccounts() {
        return userService.findValidatedUsers()
                .stream()
                .map(this::getUserDtoFromUser)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<VehicleDto> getParkedVehicles() {
        return vehicleService.findParkedVehicles()
                .stream()
                .map(this::getVehicleDtoFromVehicle)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void validateUserAccount(String username) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        User user = userService.findFirstByUsername(username);
        user.setValidated(true);
        userService.save(user);
    }

    @Override
    public void invalidateUserAccount(String username) throws ResourceNotFoundException, TooManyRequestsException, SaveException {
        User user = userService.findFirstByUsername(username);
        user.setValidated(false);
        userService.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userService.findAll()
            .stream()
            .filter(user -> !user.getRoles().contains(Role.ADMIN))
            .map(this::getUserDtoFromUser)
            .collect(Collectors.toUnmodifiableList());
    }

    private UserDto getUserDtoFromUser(User user){
        return new UserDto(user.getUserType(), user.getVehiclePlateNumbers(), user.getUsername(), user.getIsValidated());
    }

    private VehicleDto getVehicleDtoFromVehicle(Vehicle vehicle){
        return new VehicleDto(vehicle.getVehicleType(), vehicle.getPlateNumber(), vehicle.isElectric(), vehicle.isParked());
    }
}

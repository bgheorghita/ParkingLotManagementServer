package com.basware.ParkingLotManagementWeb.services.users.profile;

import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.bson.BsonString;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserDashboardImpl implements UserDashboard {

    private final UserService userService;
    private final VehicleService vehicleService;

    public UserDashboardImpl(UserService userService, VehicleService vehicleService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
    }

    @Override
    public void addVehicle(String username, VehicleDto vehicleDto) throws ResourceNotFoundException, TooManyRequestsException, SaveException {
        User user = userService.findFirstByUsername(username);
        user.addVehiclePlateNumber(vehicleDto.getPlateNumber());
        userService.save(user);

        Vehicle vehicle = new Vehicle(vehicleDto.getVehicleType(), vehicleDto.getPlateNumber(), vehicleDto.getIsElectric());
        vehicleService.save(vehicle);
    }

    @Override
    public void removeVehicle(String username, String vehiclePlateNumber) throws ResourceNotFoundException, TooManyRequestsException, SaveException {
        User user = userService.findFirstByUsername(username);
        user.removeVehiclePlateNumber(vehiclePlateNumber);
        userService.save(user);

        vehicleService.deleteByPlateNumber(vehiclePlateNumber);
    }

    @Override
    public List<VehicleDto> findAllVehicles(String username) throws ResourceNotFoundException {
        User user = userService.findFirstByUsername(username);
        List<VehicleDto> vehicles = new ArrayList<>();
        Set<String> userPlateNumbers = user.getVehiclePlateNumbers();
        for(String plateNumber : userPlateNumbers){
            Vehicle vehicle = vehicleService.findFirstByPlateNumber(plateNumber);
            VehicleType vehicleType = vehicle.getVehicleType();
            boolean vehicleIsElectric = vehicle.isElectric();
            vehicles.add(new VehicleDto(vehicleType, plateNumber, vehicleIsElectric));
        }
        return vehicles;
    }
}

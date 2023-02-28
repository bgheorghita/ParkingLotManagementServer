package com.basware.ParkingLotManagementWeb.services.users.dashboards;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.UserDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.services.tickets.TicketService;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDashboardImpl implements UserDashboard {

    private final UserService userService;
    private final VehicleService vehicleService;
    private final TicketService ticketService;

    public UserDashboardImpl(UserService userService, VehicleService vehicleService, TicketService ticketService) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.ticketService = ticketService;
    }

    @Override
    public void addVehicle(String username, VehicleDto vehicleDto) throws ResourceNotFoundException, TooManyRequestsException, SaveException {
        Vehicle vehicle = new Vehicle(vehicleDto.getVehicleType(), vehicleDto.getPlateNumber(), vehicleDto.getIsElectric());
        vehicleService.save(vehicle);

        User user = userService.findFirstByUsername(username);
        user.addVehiclePlateNumber(vehicleDto.getPlateNumber());
        userService.save(user);
    }

    @Override
    public void removeVehicle(String username, String vehiclePlateNumber) throws ResourceNotFoundException, TooManyRequestsException, SaveException, InvalidUserAction {
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(vehiclePlateNumber);
        if(vehicle.isParked()){
            throw new InvalidUserAction("Vehicles can not be removed while they are parked.");
        }

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
            boolean vehicleIsParked = vehicle.isParked();
            vehicles.add(new VehicleDto(vehicleType, plateNumber, vehicleIsElectric, vehicleIsParked));
        }
        return vehicles;
    }

    @Override
    public List<TicketDto> findAllTickets(String username) throws ResourceNotFoundException {
        List<TicketDto> tickets = new ArrayList<>();
        User user = userService.findFirstByUsername(username);
        Set<String> vehiclePlateNumbers = user.getVehiclePlateNumbers();
        for(String plateNumber : vehiclePlateNumbers){
            Vehicle vehicle = vehicleService.findFirstByPlateNumber(plateNumber);
            if(vehicle.isParked()){
                Ticket ticket = ticketService.findFirstByVehiclePlateNumber(vehicle.getPlateNumber());
                TicketDto ticketDto = TicketDto.builder()
                                .vehiclePlateNumber(plateNumber)
                                .parkingSpotNumber(ticket.getParkingSpotNumber())
                                .parkingSpotType(ticket.getParkingSpotType())
                                .timestampParkAt(Timestamp.valueOf(ticket.getStartTime()).getTime())
                                .build();
                tickets.add(ticketDto);
            }
        }
        return tickets;
    }

    @Override
    public UserDto getAccountDetails(String username) throws ResourceNotFoundException {
        User user = userService.findFirstByUsername(username);
        return UserDto.builder()
                .userType(user.getUserType())
                .vehiclePlateNumbers(user.getVehiclePlateNumbers())
                .username(user.getUsername())
                .isValidated(user.getIsValidated())
                .build();
    }
}
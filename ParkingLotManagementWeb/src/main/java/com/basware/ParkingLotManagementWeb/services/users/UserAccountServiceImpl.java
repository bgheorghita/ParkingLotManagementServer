package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.mappers.TicketMapper;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutput;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.services.tickets.TicketService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserService userService;

    @Override
    public void addVehicleToUserAccount(String username, VehicleDto vehicleDto) throws ResourceNotFoundException, TooManyRequestsException, SaveException {
        Vehicle vehicle = new Vehicle(vehicleDto.getVehicleType(), vehicleDto.getPlateNumber(), vehicleDto.getIsElectric());
        vehicleService.save(vehicle);

        User user = userService.findFirstByUsername(username);
        user.addVehiclePlateNumber(vehicleDto.getPlateNumber());
        userService.save(user);
    }

    @Override
    public List<TicketOutput> findTicketsFromUserAccount(String username) throws ResourceNotFoundException {
        List<TicketOutput> tickets = new ArrayList<>();
        User user = userService.findFirstByUsername(username);
        Set<String> vehiclePlateNumbers = user.getVehiclePlateNumbers();
        for(String plateNumber : vehiclePlateNumbers){
            Optional<TicketOutput> ticketDto = findTicket(plateNumber);
            ticketDto.ifPresent(tickets::add);
        }
        return tickets;
    }

    private Optional<TicketOutput> findTicket(String plateNumber) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(plateNumber);
        if(vehicle.getIsParked()) {
            Ticket ticket = ticketService.findFirstByVehiclePlateNumber(vehicle.getPlateNumber());
            TicketOutput ticketOutput = TicketMapper.fromTicketAndVehicleToTicketOutput(ticket, vehicle);
            return Optional.of(ticketOutput);
        }
        return Optional.empty();
    }

    @Override
    public List<Vehicle> findVehiclesFromUserAccount(String username) throws ResourceNotFoundException {
        User user = userService.findFirstByUsername(username);
        List<Vehicle> vehicles = new ArrayList<>();
        Set<String> userPlateNumbers = user.getVehiclePlateNumbers();

        for(String plateNumber : userPlateNumbers){
            Vehicle vehicle = vehicleService.findFirstByPlateNumber(plateNumber);
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    @Override
    public void removeUnparkedVehicleFromUserAccount(String username, String vehiclePlateNumber) throws ResourceNotFoundException, TooManyRequestsException, SaveException, ConflictException {
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(vehiclePlateNumber);

        if(vehicle.getIsParked()){
            throw new ConflictException("Vehicle " + vehiclePlateNumber + " can not be removed because it is parked.");
        }

        User user = userService.findFirstByUsername(username);
        user.removeVehiclePlateNumber(vehiclePlateNumber);
        userService.save(user);
        vehicleService.deleteByPlateNumber(vehiclePlateNumber);
    }
}
package com.basware.ParkingLotManagementWeb.services.users;

import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
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
    public List<Ticket> findTicketsFromUserAccount(String username) throws ResourceNotFoundException {
        List<Ticket> tickets = new ArrayList<>();
        User user = userService.findFirstByUsername(username);
        Set<String> vehiclePlateNumbers = user.getVehiclePlateNumbers();
        for(String plateNumber : vehiclePlateNumbers){
            Optional<Ticket> ticketDto = findTicket(plateNumber);
            ticketDto.ifPresent(tickets::add);
        }
        return tickets;
    }

    private Optional<Ticket> findTicket(String plateNumber) {
        try{
            Ticket ticket = ticketService.findFirstByVehiclePlateNumber(plateNumber);
            return Optional.of(ticket);
        } catch (ResourceNotFoundException e){
            return Optional.empty();
        }
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
    public void removeUnparkedVehicleFromUserAccount(String username, String vehiclePlateNumber) throws TooManyRequestsException, SaveException, ConflictException, ResourceNotFoundException {
        if(isParked(vehiclePlateNumber)){
            throw new ConflictException("Vehicle " + vehiclePlateNumber + " can not be removed because it is parked.");
        }

        User user = userService.findFirstByUsername(username);
        user.removeVehiclePlateNumber(vehiclePlateNumber);
        userService.save(user);
        vehicleService.deleteByPlateNumber(vehiclePlateNumber);
    }

    private boolean isParked(String vehiclePlateNumber){
        try{
            ticketService.findFirstByVehiclePlateNumber(vehiclePlateNumber);
            return true;
        } catch (ResourceNotFoundException ignored){
            return false;
        }
    }

}
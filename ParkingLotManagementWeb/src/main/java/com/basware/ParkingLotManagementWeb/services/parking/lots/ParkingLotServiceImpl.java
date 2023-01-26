package com.basware.ParkingLotManagementWeb.services.parking.lots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import com.basware.ParkingLotManagementWeb.services.parking.strategies.CustomParkingStrategyService;
import com.basware.ParkingLotManagementWeb.services.tickets.TicketService;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService{

    private final CustomParkingStrategyService customParkingStrategyService;
    private final ParkingSpotService parkingSpotService;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final TicketService ticketService;


    public ParkingLotServiceImpl(CustomParkingStrategyService customParkingStrategyService, ParkingSpotService parkingSpotService,
                                 VehicleService vehicleService, UserService userService, TicketService ticketService){
        this.customParkingStrategyService = customParkingStrategyService;
        this.parkingSpotService = parkingSpotService;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.ticketService = ticketService;
    }
    @Override
    public TicketOutputDto generateTicket(User user, Vehicle vehicle) throws TicketException, SaveException, ParkingSpotNotFoundException, ServiceNotAvailable {
        checkIfVehicleIsAlreadyParked(vehicle);
        ParkingSpot parkingSpot = findParkingSpotByUserTypeStrategy(user.getUserType(), vehicle);
        parkingSpot.setVehicle(vehicle);
        Ticket ticket = new Ticket(user, vehicle, parkingSpot);

        Optional<Vehicle> savedVehicleOptional = vehicleService.save(vehicle);
        Optional<User> savedUserOptional = userService.save(user);
        Optional<Ticket> savedTicketOptional = ticketService.save(ticket);

        if(savedVehicleOptional.isEmpty() || savedUserOptional.isEmpty() || savedTicketOptional.isEmpty()){
            throw new SaveException("Ticket could not be generated due to an internal server error.");
        }

        Vehicle savedVehicle = savedVehicleOptional.get();
        User savedUser = savedUserOptional.get();
        Ticket savedTicket = savedTicketOptional.get();

        try{
            parkingSpotService.save(parkingSpot);
        } catch (ConcurrentModificationException e){
            vehicleService.deleteById(savedVehicle.getObjectId());
            userService.deleteById(savedUser.getObjectId());
            ticketService.deleteById((savedTicket.getObjectId()));
            throw new ServiceNotAvailable("The service is unavailable. Please try again later.");
        }

        return new TicketOutputDto()
                .setUserName(savedUser.getName())
                .setUserType(savedUser.getUserType())
                .setVehiclePlateNumber(savedVehicle.getPlateNumber())
                .setVehicleType(savedVehicle.getVehicleType())
                .setElectricVehicle(savedVehicle.isElectric())
                .setParkingSpotType(parkingSpot.getParkingSpotType())
                .setParkingSpotWithElectricCharger(parkingSpot.hasElectricCharger())
                .setParkingSpotNumber(parkingSpot.getSpotNumber())
                .setTime(LocalDateTime.now())
                .build();
    }

    private ParkingSpot findParkingSpotByUserTypeStrategy(UserType userType, Vehicle vehicle) throws ParkingSpotNotFoundException {
        return customParkingStrategyService
                .getParkingStrategyByUserType(userType)
                .findParkingSpot(vehicle)
                .orElseThrow(() -> new ParkingSpotNotFoundException("Parking lot is full."));
    }

    private void checkIfVehicleIsAlreadyParked(Vehicle vehicle) throws VehicleAlreadyParkedException {
        try {
            vehicleService.findByPlateNumber(vehicle.getPlateNumber());
            throw new VehicleAlreadyParkedException("Vehicle with plate number " + vehicle.getPlateNumber() + " is already parked.");
        } catch (ResourceNotFoundException ignored) {}
    }
}
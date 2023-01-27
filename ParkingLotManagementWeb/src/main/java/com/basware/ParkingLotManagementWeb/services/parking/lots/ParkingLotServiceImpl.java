package com.basware.ParkingLotManagementWeb.services.parking.lots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.models.ParkingResultDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import com.basware.ParkingLotManagementWeb.services.parking.strategies.CustomParkingStrategyService;
import com.basware.ParkingLotManagementWeb.services.tickets.TicketService;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
            ticketService.deleteById(savedTicket.getObjectId());
            throw new ServiceNotAvailable("The service is unavailable. Please try again later.");
        }

        return new TicketOutputDto()
                .setTicketObjectId(savedTicket.getObjectId().toString())
                .setUserName(savedUser.getName())
                .setUserType(savedUser.getUserType())
                .setVehiclePlateNumber(savedVehicle.getPlateNumber())
                .setVehicleType(savedVehicle.getVehicleType())
                .setElectricVehicle(savedVehicle.isElectric())
                .setParkingSpotType(parkingSpot.getParkingSpotType())
                .setParkingSpotWithElectricCharger(parkingSpot.hasElectricCharger())
                .setParkingSpotNumber(parkingSpot.getSpotNumber())
                .setTime(savedTicket.getStartTime())
                .build();
    }

    @Override
    public ParkingResultDto leaveParkingLot(TicketOutputDto ticketOutputDto) throws TicketNotFoundException, ServiceNotAvailable {
        ObjectId ticketId = new ObjectId(ticketOutputDto.getTicketObjectId());
        Optional<Ticket> ticketOptional = ticketService.findById(ticketId);

        if(ticketOptional.isEmpty()){
            throw new TicketNotFoundException("The ticket id " + ticketId + " has not been found.");
        }

        Ticket ticket = ticketOptional.get();
        Vehicle vehicle = ticket.getVehicle();
        User user = ticket.getUser();
        ParkingSpot parkingSpot = ticket.getParkingSpot();
        parkingSpot.removeVehicle();

        try{
            parkingSpotService.save(parkingSpot);
            vehicleService.deleteById(vehicle.getObjectId());
            userService.deleteById(user.getObjectId());
            ticketService.deleteById(ticket.getObjectId());
        } catch (ConcurrentModificationException e){
            parkingSpot.setVehicle(vehicle);
            throw new ServiceNotAvailable("The service is unavailable. Please try again later.");
        }

        long parkingDuration = ChronoUnit.MINUTES.between(ticket.getStartTime(), LocalDateTime.now());
        return new ParkingResultDto(parkingDuration);
    }

    private ParkingSpot findParkingSpotByUserTypeStrategy(UserType userType, Vehicle vehicle) throws ParkingSpotNotFoundException {
        return customParkingStrategyService
                .getParkingStrategyByUserType(userType)
                .findParkingSpot(vehicle)
                .orElseThrow(() -> new ParkingSpotNotFoundException("No parking spot was found."));
    }

    private void checkIfVehicleIsAlreadyParked(Vehicle vehicle) throws VehicleAlreadyParkedException {
        try {
            vehicleService.findByPlateNumber(vehicle.getPlateNumber());
            throw new VehicleAlreadyParkedException("Vehicle with plate number " + vehicle.getPlateNumber() + " is already parked.");
        } catch (ResourceNotFoundException ignored) {}
    }
}
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

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
    public TicketOutputDto generateTicket(String username, String vehiclePlateNumber) throws SaveException, TooManyRequestsException, VehicleAlreadyParkedException, ResourceNotFoundException {
        User user = userService.findFirstByUsername(username);
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(vehiclePlateNumber);

        checkIfVehicleIsOwnedByUser(user, vehicle);
        checkIfVehicleIsAlreadyParked(vehicle);

        ParkingSpot parkingSpot = findParkingSpotByUserTypeStrategy(user.getUserType(), vehicle);
        parkingSpot.setVehiclePlateNumber(vehiclePlateNumber);
        parkingSpotService.save(parkingSpot);

        vehicle.setVehicleIsParked(true);
        vehicleService.save(vehicle);

        Ticket ticket = new Ticket(user.getUsername(), vehicle.getPlateNumber(), parkingSpot.getSpotNumber());
        Ticket savedTicket = ticketService.save(ticket);

        return new TicketOutputDto()
                .withUserName(user.getUsername())
                .withUserType(user.getUserType())
                .withVehiclePlateNumber(vehicle.getPlateNumber())
                .withVehicleType(vehicle.getVehicleType())
                .withElectricVehicle(vehicle.isElectric())
                .withParkingSpotType(parkingSpot.getParkingSpotType())
                .withParkingSpotWithElectricCharger(parkingSpot.hasElectricCharger())
                .withParkingSpotNumber(parkingSpot.getSpotNumber())
                .withTime(savedTicket.getStartTime())
                .build();
    }

    private void checkIfVehicleIsOwnedByUser(User user, Vehicle vehicle) throws ResourceNotFoundException {
        String username = user.getUsername();
        String plateNumber = vehicle.getPlateNumber();
        Set<String> usersPlateNumbers = user.getVehiclePlateNumbers();

        if(!usersPlateNumbers.contains(plateNumber)){
            throw new ResourceNotFoundException("Vehicle " + plateNumber + " does not appear to be owned by " + username);
        }
    }

    @Override
    public ParkingResultDto leaveParkingLot(String username, String vehiclePlateNumber) throws TooManyRequestsException, SaveException, ResourceNotFoundException, VehicleNotParkedException {
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(vehiclePlateNumber);
        User user = userService.findFirstByUsername(username);

        checkIfVehicleIsOwnedByUser(user, vehicle);
        checkIfVehicleIsNotParked(vehicle);

        ParkingSpot parkingSpot = parkingSpotService.findFirstByVehiclePlateNumber(vehiclePlateNumber);
        Ticket ticket = ticketService.findFirstByVehiclePlateNumber(vehiclePlateNumber);

        parkingSpot.removeVehiclePlateNumber();
        parkingSpotService.save(parkingSpot);

        vehicle.setVehicleIsParked(false);
        vehicleService.save(vehicle);

        ticketService.deleteById(ticket.getObjectId());

        long parkingDuration = ChronoUnit.MINUTES.between(ticket.getStartTime(), LocalDateTime.now());
        return new ParkingResultDto(parkingDuration);
    }

    private void checkIfVehicleIsNotParked(Vehicle vehicle) throws VehicleNotParkedException {
        if(!vehicle.isParked()){
            throw new VehicleNotParkedException("Vehicle " + vehicle.getPlateNumber() + " does not seem to be parked.");
        }
    }

    private ParkingSpot findParkingSpotByUserTypeStrategy(UserType userType, Vehicle vehicle) throws FullParkingLotException {
        return customParkingStrategyService
                .getParkingStrategyByUserType(userType)
                .findParkingSpot(vehicle)
                .orElseThrow(() -> new FullParkingLotException("No parking spot was found."));
    }

    private void checkIfVehicleIsAlreadyParked(Vehicle vehicle) throws VehicleAlreadyParkedException {
        if(vehicle.isParked()){
            throw new VehicleAlreadyParkedException(String.format("Vehicle with plate number \"%s\" is already parked.",
                    vehicle.getPlateNumber()));
        }
    }
}
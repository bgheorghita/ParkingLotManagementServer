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
    public TicketOutputDto generateTicket(User user, Vehicle vehicle) throws SaveException, TooManyRequestsException, VehicleAlreadyParkedException, FullParkingLotException {
        checkIfVehicleIsAlreadyParked(vehicle);

        ParkingSpot parkingSpot = findParkingSpotByUserTypeStrategy(user.getUserType(), vehicle);
        parkingSpot.setVehiclePlateNumber(vehicle.getPlateNumber());
        parkingSpotService.save(parkingSpot);

        Ticket ticket = new Ticket(user.getName(), vehicle.getPlateNumber(), parkingSpot.getSpotNumber());
        Ticket savedTicket = ticketService.save(ticket);

        Vehicle savedVehicle = vehicleService.save(vehicle);

        user.setVehiclePlateNumber(vehicle.getPlateNumber());
        User savedUser = userService.save(user);

        return new TicketOutputDto()
                .withUserName(savedUser.getName())
                .withUserType(savedUser.getUserType())
                .withVehiclePlateNumber(savedVehicle.getPlateNumber())
                .withVehicleType(savedVehicle.getVehicleType())
                .withElectricVehicle(savedVehicle.isElectric())
                .withParkingSpotType(parkingSpot.getParkingSpotType())
                .withParkingSpotWithElectricCharger(parkingSpot.hasElectricCharger())
                .withParkingSpotNumber(parkingSpot.getSpotNumber())
                .withTime(savedTicket.getStartTime())
                .build();
    }

    @Override
    public ParkingResultDto leaveParkingLot(String vehiclePlateNumber) throws TooManyRequestsException, SaveException, ResourceNotFoundException {
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(vehiclePlateNumber);
        User user = userService.findFirstByVehiclePlateNumber(vehiclePlateNumber);
        ParkingSpot parkingSpot = parkingSpotService.findFirstByVehiclePlateNumber(vehiclePlateNumber);
        Ticket ticket = ticketService.findFirstByVehiclePlateNumber(vehiclePlateNumber);

        parkingSpot.removeVehiclePlateNumber();
        parkingSpotService.save(parkingSpot);

        vehicleService.deleteById(vehicle.getObjectId());
        userService.deleteById(user.getObjectId());
        ticketService.deleteById(ticket.getObjectId());

        long parkingDuration = ChronoUnit.MINUTES.between(ticket.getStartTime(), LocalDateTime.now());
        return new ParkingResultDto(parkingDuration);
    }

    private ParkingSpot findParkingSpotByUserTypeStrategy(UserType userType, Vehicle vehicle) throws FullParkingLotException {
        return customParkingStrategyService
                .getParkingStrategyByUserType(userType)
                .findParkingSpot(vehicle)
                .orElseThrow(() -> new FullParkingLotException("No parking spot was found."));
    }

    private void checkIfVehicleIsAlreadyParked(Vehicle vehicle) throws VehicleAlreadyParkedException {
        String vehiclePlateNumber = vehicle.getPlateNumber();
        try {
            vehicleService.findFirstByPlateNumber(vehiclePlateNumber);
            throw new VehicleAlreadyParkedException(String.format("Vehicle with plate number \"%s\" is already parked.", vehiclePlateNumber));
        } catch (ResourceNotFoundException ignored) {}
    }
}
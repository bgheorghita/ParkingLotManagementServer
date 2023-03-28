package com.basware.ParkingLotManagementWeb.services.parking.lots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.api.v1.models.ParkingResultDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import com.basware.ParkingLotManagementWeb.services.parking.strategies.CustomParkingStrategyService;
import com.basware.ParkingLotManagementWeb.services.taxes.calculators.ParkingPriceService;
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
    private final ParkingPriceService parkingPriceService;

    public ParkingLotServiceImpl(CustomParkingStrategyService customParkingStrategyService, ParkingSpotService parkingSpotService,
                                 VehicleService vehicleService, UserService userService, TicketService ticketService,
                                 ParkingPriceService parkingPriceService){
        this.customParkingStrategyService = customParkingStrategyService;
        this.parkingSpotService = parkingSpotService;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.ticketService = ticketService;
        this.parkingPriceService = parkingPriceService;
    }

    @Override
    public TicketOutputDto generateTicket(String username, String vehiclePlateNumber) throws UnauthorizedException, ResourceNotFoundException, VehicleAlreadyParkedException, TooManyRequestsException, SaveException {
        User user = userService.findFirstByUsername(username);
        checkIfUserIsValidated(user);
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(vehiclePlateNumber);

        checkIfVehicleIsOwnedByUser(user, vehicle);
        checkIfVehicleIsAlreadyParked(vehicle);

        ParkingSpot parkingSpot = findParkingSpotByUserTypeStrategy(user.getUserType(), vehicle);
        parkingSpot.setVehiclePlateNumber(vehiclePlateNumber);
        parkingSpotService.save(parkingSpot);

        Ticket ticket = new Ticket(user.getUsername(), vehicle.getPlateNumber(), parkingSpot.getSpotNumber(), parkingSpot.getParkingSpotType(), parkingSpot.hasElectricCharger());
        Ticket savedTicket = ticketService.save(ticket);

        return new TicketOutputDto()
                .withUserName(user.getUsername())
                .withUserType(user.getUserType())
                .withVehiclePlateNumber(vehicle.getPlateNumber())
                .withVehicleType(vehicle.getVehicleType())
                .withElectricVehicle(vehicle.getIsElectric())
                .withParkingSpotType(parkingSpot.getParkingSpotType())
                .withParkingSpotWithElectricCharger(parkingSpot.hasElectricCharger())
                .withParkingSpotNumber(parkingSpot.getSpotNumber())
                .withTime(savedTicket.getStartTime())
                .build();
    }

    private void checkIfUserIsValidated(User user) throws UnauthorizedException {
        if(!user.getIsValidated()){
            throw new UnauthorizedException("You can not park while your account is not validated.");
        }
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
    public ParkingResultDto leaveParkingLot(String username, String vehiclePlateNumber) throws ResourceNotFoundException, VehicleNotParkedException, TooManyRequestsException, SaveException, ServiceNotAvailable {
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(vehiclePlateNumber);
        User user = userService.findFirstByUsername(username);

        checkIfVehicleIsOwnedByUser(user, vehicle);
        checkIfVehicleIsNotParked(vehicle);

        ParkingSpot parkingSpot = parkingSpotService.findFirstByVehiclePlateNumber(vehiclePlateNumber);
        Ticket ticket = ticketService.findFirstByVehiclePlateNumber(vehiclePlateNumber);

        parkingSpot.removeVehiclePlateNumber();
        parkingSpotService.save(parkingSpot);

        ticketService.deleteById(ticket.getObjectId());

        long parkingDuration = ChronoUnit.MINUTES.between(ticket.getStartTime(), LocalDateTime.now());
        Price price = parkingPriceService.getParkingPrice(parkingDuration, user.getUserType(), vehicle.getVehicleType(), parkingSpot.getParkingSpotType(), Currency.EUR);
        return new ParkingResultDto(parkingDuration, price);
    }

    private void checkIfVehicleIsNotParked(Vehicle vehicle) throws VehicleNotParkedException {
        try {
            parkingSpotService.findFirstByVehiclePlateNumber(vehicle.getPlateNumber());
        } catch (ResourceNotFoundException e) {
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
        try {
            parkingSpotService.findFirstByVehiclePlateNumber(vehicle.getPlateNumber());
            throw new VehicleAlreadyParkedException(String.format("Vehicle with plate number \"%s\" is already parked.",
                    vehicle.getPlateNumber()));
        } catch (ResourceNotFoundException ignored) {}
    }
}
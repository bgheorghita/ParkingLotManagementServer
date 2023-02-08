package com.basware.ParkingLotManagementWeb.services.parking.lots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.api.v1.models.ParkingResultDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import com.basware.ParkingLotManagementWeb.services.tickets.TicketService;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import dev.morphia.Datastore;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles="test")
class ParkingLotServiceImplITest {

    @Autowired
    private ParkingLotServiceImpl parkingLotService;

    @Autowired
    private Datastore datastore;

    @Autowired
    private ParkingSpotService parkingSpotService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    private User regularUser;
    private Vehicle electricCar;
    private ParkingSpot mediumParkingSpotWithElectricCharger;

    @BeforeEach
    void setUp() {
        regularUser = new User("testRegularUser", UserType.REGULAR);
        electricCar = new Vehicle(VehicleType.CAR, "plateNumberTest", true);
        mediumParkingSpotWithElectricCharger = new ParkingSpot(ParkingSpotType.MEDIUM, 1L, true);
    }

    @AfterEach
    void tearUp(){
       clearCollections();
    }

    @Test
    void generateTicket_ShouldThrowVehicleAlreadyParkedExceptionWhenItsPlateNumberExistsInDatabase(){
        datastore.save(electricCar);
        assertThrowsExactly(VehicleAlreadyParkedException.class, () -> parkingLotService.generateTicket(regularUser, electricCar));
    }

    @Test
    void generateTicket_ShouldThrowFullParkingLotExceptionWhenDidNotFindAnyAvailableParkingSpot(){
        assertThrowsExactly(FullParkingLotException.class, () -> parkingLotService.generateTicket(regularUser, electricCar));
    }

    @Test
    void generateTicket_ShouldReturnTicketWithRegularUserAndElectricCarDetailsAndSaveToDatabase() throws SaveException, TooManyRequestsException, VehicleAlreadyParkedException, ResourceNotFoundException {
        datastore.save(mediumParkingSpotWithElectricCharger);
        TicketOutputDto ticketOutputDto = parkingLotService.generateTicket(regularUser, electricCar);

        // checks if ticket has been saved in database
        Ticket ticket = ticketService.findFirstByVehiclePlateNumber(electricCar.getPlateNumber());
        assertNotNull(ticket);

        // checks if user has been saved in database
        User user = userService.findFirstByVehiclePlateNumber(electricCar.getPlateNumber());
        assertNotNull(user);

        // checks if vehicle has been saved in database
        Vehicle vehicle = vehicleService.findFirstByPlateNumber(electricCar.getPlateNumber());
        assertNotNull(vehicle);

        // checks ticket output details
        assertEquals(mediumParkingSpotWithElectricCharger.getSpotNumber(), ticketOutputDto.getParkingSpotNumber());
        assertEquals(mediumParkingSpotWithElectricCharger.getParkingSpotType(), ticketOutputDto.getParkingSpotType());
        assertEquals(mediumParkingSpotWithElectricCharger.hasElectricCharger(), ticketOutputDto.isElectricVehicle());
        assertEquals(electricCar.getPlateNumber(), ticketOutputDto.getVehiclePlateNumber());
        assertEquals(regularUser.getUserType(), ticketOutputDto.getUserType());
        assertEquals(regularUser.getName(), ticketOutputDto.getUserName());
        assertEquals(regularUser.getVehiclePlateNumber(), ticketOutputDto.getVehiclePlateNumber());
    }

    @Test
    void leaveParkingLot_ShouldThrowResourceNotFoundWhenVehiclePlateNumberIsNotSavedInDatabase(){
        String vehiclePlateNumber = "thisPlateNumberIsNotSavedInDatabase";
        assertThrowsExactly(ResourceNotFoundException.class, () -> parkingLotService.leaveParkingLot(vehiclePlateNumber));
    }

    @Test
    void leaveParkingLot_ShouldReturnParkingResultDtoWhenTheTicketIsGeneratedAndDeleteFromDatabase() throws SaveException, TooManyRequestsException, VehicleAlreadyParkedException, ResourceNotFoundException {
        datastore.save(mediumParkingSpotWithElectricCharger);
        parkingLotService.generateTicket(regularUser, electricCar);
        ParkingResultDto parkingResultDto = parkingLotService.leaveParkingLot(electricCar.getPlateNumber());

        assertNotNull(parkingResultDto);

        // checks if ticket has been deleted from database
        assertThrowsExactly(ResourceNotFoundException.class, () ->
                ticketService.findFirstByVehiclePlateNumber(electricCar.getPlateNumber()));

        // checks if user has been deleted from database
        assertThrowsExactly(ResourceNotFoundException.class, () ->
                userService.findFirstByVehiclePlateNumber(electricCar.getPlateNumber()));

        // checks if vehicle has been deleted from database
        assertThrowsExactly(ResourceNotFoundException.class, () ->
                vehicleService.findFirstByPlateNumber(electricCar.getPlateNumber()));

        // checks if parking spot from database has been freed
        ObjectId parkingSpotObjectId = mediumParkingSpotWithElectricCharger.getObjectId();
        Optional<ParkingSpot> parkingSpotInDatabase = parkingSpotService.findById(parkingSpotObjectId);
        assertTrue(parkingSpotInDatabase.isPresent());
        assertTrue(parkingSpotInDatabase.get().isFree());
    }

    private void clearCollections(){
        datastore.getDatabase()
                .listCollectionNames()
                .forEach(a -> datastore
                        .getDatabase()
                        .getCollection(a)
                        .deleteMany(new BsonDocument()));
    }
}
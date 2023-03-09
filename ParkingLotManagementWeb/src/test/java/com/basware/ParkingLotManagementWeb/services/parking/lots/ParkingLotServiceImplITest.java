package com.basware.ParkingLotManagementWeb.services.parking.lots;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementCommon.models.taxes.TypePrice;
import com.basware.ParkingLotManagementCommon.models.tickets.Ticket;
import com.basware.ParkingLotManagementCommon.models.users.Role;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.api.v1.models.ParkingResultDto;
import com.basware.ParkingLotManagementWeb.api.v1.models.TicketOutputDto;
import com.basware.ParkingLotManagementWeb.exceptions.*;
import com.basware.ParkingLotManagementWeb.repositories.taxes.TypePriceDao;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private TypePriceDao typePriceDao;

    private User regularUser;
    private Vehicle electricCar;
    private ParkingSpot mediumParkingSpotWithElectricCharger;

    @BeforeEach
    void setUp() {
        electricCar = new Vehicle(VehicleType.CAR, "plateNumberTest", true);
        regularUser = new User("testRegularUser", Set.of(Role.REGULAR), UserType.REGULAR, "", true);
        Set<String> vehiclePlateNumbers = new HashSet<>();
        vehiclePlateNumbers.add(electricCar.getPlateNumber());
        regularUser.setVehiclePlateNumbers(vehiclePlateNumbers);
        mediumParkingSpotWithElectricCharger = new ParkingSpot(ParkingSpotType.MEDIUM, 1L, true);
        datastore.save(regularUser);
        datastore.save(electricCar);
    }

    @AfterEach
    void tearUp(){
       clearCollections();
    }

    @Test
    void generateTicket_ShouldThrowVehicleAlreadyParkedExceptionWhenTheVehicleIsAlreadyParked() throws SaveException, TooManyRequestsException, VehicleAlreadyParkedException, ResourceNotFoundException, UnauthorizedException {
        datastore.save(mediumParkingSpotWithElectricCharger);
        parkingLotService.generateTicket(regularUser.getUsername(), electricCar.getPlateNumber());

        assertThrowsExactly(VehicleAlreadyParkedException.class, () -> parkingLotService.generateTicket(regularUser.getUsername(), electricCar.getPlateNumber()));
    }

    @Test
    void generateTicket_ShouldThrowFullParkingLotExceptionWhenDidNotFindAnyAvailableParkingSpot(){
        assertThrowsExactly(FullParkingLotException.class, () -> parkingLotService.generateTicket(regularUser.getUsername(), electricCar.getPlateNumber()));
    }

    @Test
    void generateTicket_ShouldReturnTicketWithRegularUserAndElectricCarDetailsAndSaveToDatabase() throws SaveException, TooManyRequestsException, VehicleAlreadyParkedException, ResourceNotFoundException, UnauthorizedException {
        datastore.save(mediumParkingSpotWithElectricCharger);
        TicketOutputDto ticketOutputDto = parkingLotService.generateTicket(regularUser.getUsername(), electricCar.getPlateNumber());

        // checks if ticket has been saved in database
        Ticket ticket = ticketService.findFirstByVehiclePlateNumber(electricCar.getPlateNumber());
        assertNotNull(ticket);

        // checks ticket output details
        assertEquals(mediumParkingSpotWithElectricCharger.getSpotNumber(), ticketOutputDto.getParkingSpotNumber());
        assertEquals(mediumParkingSpotWithElectricCharger.getParkingSpotType(), ticketOutputDto.getParkingSpotType());
        assertEquals(mediumParkingSpotWithElectricCharger.hasElectricCharger(), ticketOutputDto.getIsElectricVehicle());
        assertEquals(electricCar.getPlateNumber(), ticketOutputDto.getVehiclePlateNumber());
        assertEquals(regularUser.getUserType(), ticketOutputDto.getUserType());
        assertEquals(regularUser.getUsername(), ticketOutputDto.getUsername());
        assertEquals(regularUser.getVehiclePlateNumbers().toArray()[0], ticketOutputDto.getVehiclePlateNumber());
    }

    @Test
    void leaveParkingLot_ShouldThrowVehicleNotParkedExceptionWhenVehicleIsNotParked(){
        assertThrowsExactly(VehicleNotParkedException.class, () -> parkingLotService.leaveParkingLot(regularUser.getUsername(), electricCar.getPlateNumber()));
    }

    @Test
    void leaveParkingLot_ShouldReturnParkingPriceWhenTheTicketIsGeneratedAndShouldDeleteTheTicketFromDatabase() throws SaveException, TooManyRequestsException, VehicleAlreadyParkedException, ResourceNotFoundException, VehicleNotParkedException, ServiceNotAvailable, UnauthorizedException {
        datastore.save(mediumParkingSpotWithElectricCharger);
        parkingLotService.generateTicket(regularUser.getUsername(), electricCar.getPlateNumber());

        // set up the needed prices for this case
        Price price = new Price(1, Currency.EUR);
        TypeInfo typeInfo = new TypeInfo("user", "REGULAR");
        TypePrice typePrice = new TypePrice(typeInfo, price);
        typePriceDao.save(typePrice);

        Price price1 = new Price(2, Currency.EUR);
        TypeInfo typeInfo1 = new TypeInfo("vehicle", "CAR");
        TypePrice typePrice1 = new TypePrice(typeInfo1, price1);
        typePriceDao.save(typePrice1);

        Price price2 = new Price(3, Currency.EUR);
        TypeInfo typeInfo2 = new TypeInfo("parkingSpot", "MEDIUM");
        TypePrice typePrice2 = new TypePrice(typeInfo2, price2);
        typePriceDao.save(typePrice2);

        ParkingResultDto parkingResultDto = parkingLotService.leaveParkingLot(regularUser.getUsername(), electricCar.getPlateNumber());

        assertNotNull(parkingResultDto);

        // checks if ticket has been deleted from database
        assertThrowsExactly(ResourceNotFoundException.class, () ->
                ticketService.findFirstByVehiclePlateNumber(electricCar.getPlateNumber()));

        // checks if parking spot from database has been freed
        ObjectId parkingSpotObjectId = mediumParkingSpotWithElectricCharger.getObjectId();
        Optional<ParkingSpot> parkingSpotInDatabase = parkingSpotService.findById(parkingSpotObjectId);
        assertTrue(parkingSpotInDatabase.isPresent());
        assertTrue(parkingSpotInDatabase.get().isFree());
    }

    private void clearCollections(){
        datastore.getDatabase()
        .listCollectionNames()
        .forEach(a ->  datastore
                        .getDatabase()
                        .getCollection(a)
                        .deleteMany(new BsonDocument())
        );
    }
}
package com.basware.ParkingLotManagementWeb.services.parking.strategies;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import dev.morphia.Datastore;
import org.bson.BsonDocument;
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
class VipUserParkingStrategyIT {

    @Autowired
    private VipUserParkingStrategy vipUserParkingStrategy;

    @Autowired
    private Datastore datastore;

    @Autowired
    private ParkingSpotService parkingSpotService;

    private Vehicle nonElectricMotorcycle;
    private Vehicle electricMotorcycle;
    private Vehicle nonElectricCar;
    private Vehicle electricCar;
    private Vehicle nonElectricTruck;
    private Vehicle electricTruck;

    private ParkingSpot smallParkingSpotWithoutElectricCharger;
    private ParkingSpot mediumParkingSpotWithoutElectricCharger;
    private ParkingSpot largeParkingSpotWithoutElectricCharger;

    private ParkingSpot smallParkingSpotWithElectricCharger;
    private ParkingSpot mediumParkingSpotWithElectricCharger;
    private ParkingSpot largeParkingSpotWithElectricCharger;

    @BeforeEach
    void setUp() {
        nonElectricMotorcycle = new Vehicle(VehicleType.MOTORCYCLE, "MO_PLATE", false);
        electricMotorcycle = new Vehicle(VehicleType.MOTORCYCLE, "EL_MO_PLATE", true);
        nonElectricCar = new Vehicle(VehicleType.CAR, "CA_PLATE", false);
        electricCar = new Vehicle(VehicleType.CAR, "EL_CA_PLATE", true);
        nonElectricTruck = new Vehicle(VehicleType.TRUCK, "TR_PLATE", false);
        electricTruck = new Vehicle(VehicleType.TRUCK, "EL_TR_PLATE", true);

        smallParkingSpotWithoutElectricCharger = new ParkingSpot(ParkingSpotType.SMALL, 1L, false);
        mediumParkingSpotWithoutElectricCharger = new ParkingSpot(ParkingSpotType.MEDIUM, 2L, false);
        largeParkingSpotWithoutElectricCharger = new ParkingSpot(ParkingSpotType.LARGE, 3L, false);

        smallParkingSpotWithElectricCharger = new ParkingSpot(ParkingSpotType.SMALL, 4L, true);
        mediumParkingSpotWithElectricCharger = new ParkingSpot(ParkingSpotType.MEDIUM, 5L, true);
        largeParkingSpotWithElectricCharger = new ParkingSpot(ParkingSpotType.LARGE, 6L, true);

        datastore.save(smallParkingSpotWithElectricCharger);
        datastore.save(mediumParkingSpotWithElectricCharger);
        datastore.save(largeParkingSpotWithElectricCharger);
        datastore.save(smallParkingSpotWithoutElectricCharger);
        datastore.save(mediumParkingSpotWithoutElectricCharger);
        datastore.save(largeParkingSpotWithoutElectricCharger);
    }

    @AfterEach
    void tearDown() {
        clearCollections();
    }

    @Test
    void findParkingSpot_ShouldReturnEmptyOptionalForElectricMotorcycleWhenThereIsNoParkingSpot(){
        deleteAllParkingSpots();

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isEmpty());
 }

    @Test
    void findParkingSpot_ShouldReturnSmallParkingSpotWithElectricChargerForElectricMotorcycle(){
        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.SMALL, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithElectricChargerForElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithElectricChargerForElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithElectricCharger);
        datastore.delete(mediumParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnSmallParkingSpotWithoutElectricChargerForElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithElectricCharger);
        datastore.delete(mediumParkingSpotWithElectricCharger);
        datastore.delete(largeParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.SMALL, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithoutElectricChargerForElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithElectricCharger);
        datastore.delete(mediumParkingSpotWithElectricCharger);
        datastore.delete(largeParkingSpotWithElectricCharger);
        datastore.delete(smallParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithoutElectricChargerForElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithElectricCharger);
        datastore.delete(mediumParkingSpotWithElectricCharger);
        datastore.delete(largeParkingSpotWithElectricCharger);
        datastore.delete(smallParkingSpotWithoutElectricCharger);
        datastore.delete(mediumParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithElectricChargerForElectricCar(){
        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricCar);
        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithElectricChargerForElectricCar(){
        datastore.delete(mediumParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricCar);
        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithoutElectricChargerForElectricCar(){
        datastore.delete(mediumParkingSpotWithElectricCharger);
        datastore.delete(largeParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricCar);
        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithoutElectricChargerForElectricCar(){
        datastore.delete(mediumParkingSpotWithElectricCharger);
        datastore.delete(mediumParkingSpotWithoutElectricCharger);
        datastore.delete(largeParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricCar);
        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithElectricChargerForElectricTruck(){
        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricTruck);

        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithoutElectricChargerForElectricTruck(){
        datastore.delete(largeParkingSpotWithElectricCharger);
        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricTruck);

        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnEmptyOptionalForElectricTruckWhenThereIsNoParkingSpotAvailableForElectricTruck(){
        datastore.delete(largeParkingSpotWithElectricCharger);
        datastore.delete(largeParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(electricTruck);
        assertTrue(parkingSpotOptional.isEmpty());
    }

    @Test
    void findParkingSpot_ShouldReturnEmptyOptionalForNonElectricMotorcycleWhenThereIsNotParkingSpotAvailable(){
        deleteAllParkingSpots();
        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);
        assertTrue(parkingSpotOptional.isEmpty());
    }

    @Test
    void findParkingSpot_ShouldReturnSmallParkingSpotWithoutElectricChargerForNonElectricMotorcycle(){
        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);

        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.SMALL, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithoutElectricChargerForNonElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);

        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithoutElectricChargerForNonElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithoutElectricCharger);
        datastore.delete(mediumParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);

        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnSmallParkingSpotWithElectricChargerForNonElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithoutElectricCharger);
        datastore.delete(mediumParkingSpotWithoutElectricCharger);
        datastore.delete(largeParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);

        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.SMALL, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithElectricChargerForNonElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithoutElectricCharger);
        datastore.delete(mediumParkingSpotWithoutElectricCharger);
        datastore.delete(largeParkingSpotWithoutElectricCharger);
        datastore.delete(smallParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);

        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithElectricChargerForNonElectricMotorcycle(){
        datastore.delete(smallParkingSpotWithoutElectricCharger);
        datastore.delete(mediumParkingSpotWithoutElectricCharger);
        datastore.delete(largeParkingSpotWithoutElectricCharger);
        datastore.delete(smallParkingSpotWithElectricCharger);
        datastore.delete(mediumParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);

        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithoutElectricChargerForNonElectricCar(){
        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricCar);

        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithoutElectricChargerForNonElectricCar(){
        datastore.delete(mediumParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricCar);

        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithElectricChargerForNonElectricCar(){
        datastore.delete(mediumParkingSpotWithoutElectricCharger);
        datastore.delete(largeParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricCar);

        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithElectricChargerForNonElectricCar(){
        datastore.delete(mediumParkingSpotWithoutElectricCharger);
        datastore.delete(largeParkingSpotWithoutElectricCharger);
        datastore.delete(mediumParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricCar);

        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnEmptyOptionalForNonElectricTruckWhenThereIsNotAvailableParkingSpotForNonElectricTruck(){
        deleteAllParkingSpots();

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricTruck);
        assertTrue(parkingSpotOptional.isEmpty());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithoutElectricChargerForNonElectricTruck(){
        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricTruck);

        assertTrue(parkingSpotOptional.isPresent());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithElectricChargerForNonElectricTruck(){
        datastore.delete(largeParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = vipUserParkingStrategy.findParkingSpot(nonElectricTruck);

        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
    }

    private void clearCollections(){
        datastore.getDatabase()
                .listCollectionNames()
                .forEach(a -> datastore
                        .getDatabase()
                        .getCollection(a)
                        .deleteMany(new BsonDocument()));
    }

    private void deleteAllParkingSpots(){
        datastore.delete(smallParkingSpotWithElectricCharger);
        datastore.delete(mediumParkingSpotWithElectricCharger);
        datastore.delete(largeParkingSpotWithElectricCharger);

        datastore.delete(smallParkingSpotWithoutElectricCharger);
        datastore.delete(mediumParkingSpotWithoutElectricCharger);
        datastore.delete(largeParkingSpotWithoutElectricCharger);
    }
}
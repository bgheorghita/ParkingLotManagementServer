package com.basware.ParkingLotManagementWeb.services.parking.strategies;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
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
class RegularUserParkingStrategyITest {

    @Autowired
    private RegularUserParkingStrategy regularUserParkingStrategy;

    @Autowired
    private Datastore datastore;

    private ParkingSpot smallParkingSpotWithoutElectricCharger;
    private ParkingSpot mediumParkingSpotWithoutElectricCharger;
    private ParkingSpot largeParkingSpotWithoutElectricCharger;

    private ParkingSpot smallParkingSpotWithElectricCharger;
    private ParkingSpot mediumParkingSpotWithElectricCharger;
    private ParkingSpot largeParkingSpotWithElectricCharger;

    private Vehicle nonElectricMotorcycle;
    private Vehicle electricMotorcycle;
    private Vehicle nonElectricCar;
    private Vehicle electricCar;
    private Vehicle nonElectricTruck;
    private Vehicle electricTruck;



    @BeforeEach
    void setUp(){
        smallParkingSpotWithoutElectricCharger = new ParkingSpot(ParkingSpotType.SMALL, 1L, false);
        mediumParkingSpotWithoutElectricCharger = new ParkingSpot(ParkingSpotType.MEDIUM, 2L, false);
        largeParkingSpotWithoutElectricCharger = new ParkingSpot(ParkingSpotType.LARGE, 3L, false);

        smallParkingSpotWithElectricCharger = new ParkingSpot(ParkingSpotType.SMALL, 4L, true);
        mediumParkingSpotWithElectricCharger = new ParkingSpot(ParkingSpotType.MEDIUM, 5L, true);
        largeParkingSpotWithElectricCharger = new ParkingSpot(ParkingSpotType.LARGE, 6L, true);

        nonElectricMotorcycle = new Vehicle(VehicleType.MOTORCYCLE, "PLATE_MOTORCYCLE", false);
        electricMotorcycle = new Vehicle(VehicleType.MOTORCYCLE, "PLATE_MOTORCYCLE_EL", true);
        nonElectricCar = new Vehicle(VehicleType.CAR, "PLATE_CAR", false);
        electricCar = new Vehicle(VehicleType.CAR, "PLATE_CAR_EL", true);
        nonElectricTruck = new Vehicle(VehicleType.TRUCK, "PLATE_TRUCK", false);
        electricTruck = new Vehicle(VehicleType.TRUCK, "PLATE_TRUCK_EL", true);
    }

    @AfterEach
    void tearDown(){
        clearCollections();
    }

    @Test
    void findParkingSpot_ShouldReturnEmptyOptionalWhenThereIsNotParkingSpotForNonElectricMotorcycle(){
        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);
        assertTrue(parkingSpotOptional.isEmpty());
    }

    @Test
    void findParkingSpot_ShouldReturnSmallParkingSpotWithElectricChargerForElectricMotorcycle(){
        datastore.save(smallParkingSpotWithElectricCharger);
        datastore.save(smallParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.SMALL, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnEmptyOptionalForElectricMotorcycleWhenThereAreParkingSpotsButNotForElectricMotorcycle(){
        datastore.save(mediumParkingSpotWithElectricCharger);
        datastore.save(mediumParkingSpotWithoutElectricCharger);
        datastore.save(largeParkingSpotWithElectricCharger);
        datastore.save(largeParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(electricMotorcycle);
        assertTrue(parkingSpotOptional.isEmpty());
    }

    @Test
    void findParkingSpot_ShouldReturnSmallParkingSpotWithElectricChargerForNonElectricMotorcycle(){
        datastore.save(smallParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(nonElectricMotorcycle);
        assertTrue(parkingSpotOptional.isPresent());
        assertEquals(ParkingSpotType.SMALL, parkingSpotOptional.get().getParkingSpotType());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithElectricChargerForElectricCarWhenThereIsSuchParkingSpot(){
        datastore.save(mediumParkingSpotWithoutElectricCharger);
        datastore.save(mediumParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(electricCar);
        assertTrue(parkingSpotOptional.isPresent());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
    }

    @Test
    void findParkingSpot_ShouldReturnEmptyOptionalForElectricCarWhenThereAreParkingSpotsButNotForElectricCar(){
        datastore.save(smallParkingSpotWithElectricCharger);
        datastore.save(smallParkingSpotWithoutElectricCharger);
        datastore.save(largeParkingSpotWithoutElectricCharger);
        datastore.save(largeParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(electricCar);
        assertTrue(parkingSpotOptional.isEmpty());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithoutElectricChargerForElectricCar(){
        datastore.save(mediumParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> optionalParkingSpot = regularUserParkingStrategy.findParkingSpot(electricCar);
        assertTrue(optionalParkingSpot.isPresent());
        assertEquals(ParkingSpotType.MEDIUM, optionalParkingSpot.get().getParkingSpotType());
        assertFalse(optionalParkingSpot.get().hasElectricCharger());
    }

    @Test
    void findParkingSpot_ShouldReturnMediumParkingSpotWithElectricChargerForNonElectricCar(){
        datastore.save(mediumParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(nonElectricCar);
        assertTrue(parkingSpotOptional.isPresent());
        assertEquals(ParkingSpotType.MEDIUM, parkingSpotOptional.get().getParkingSpotType());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
    }

    @Test
    void findParkingSpot_ShouldReturnEmptyOptionalForElectricTruckWhenThereIsNotParkingSpotForElectricTruck(){
        datastore.save(smallParkingSpotWithElectricCharger);
        datastore.save(smallParkingSpotWithoutElectricCharger);
        datastore.save(mediumParkingSpotWithElectricCharger);
        datastore.save(mediumParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(electricTruck);
        assertTrue(parkingSpotOptional.isEmpty());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithElectricChargerForElectricTruckWhenThereIsSuchParkingSpot(){
        datastore.save(largeParkingSpotWithElectricCharger);
        datastore.save(largeParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(electricTruck);
        assertTrue(parkingSpotOptional.isPresent());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
        assertTrue(parkingSpotOptional.get().hasElectricCharger());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithoutElectricChargerForElectricTruck(){
        datastore.save(largeParkingSpotWithoutElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(electricTruck);
        assertTrue(parkingSpotOptional.isPresent());
        assertEquals(ParkingSpotType.LARGE, parkingSpotOptional.get().getParkingSpotType());
        assertFalse(parkingSpotOptional.get().hasElectricCharger());
    }

    @Test
    void findParkingSpot_ShouldReturnLargeParkingSpotWithElectricChargerForNonElectricTruck(){
        datastore.save(largeParkingSpotWithElectricCharger);

        Optional<ParkingSpot> parkingSpotOptional = regularUserParkingStrategy.findParkingSpot(nonElectricTruck);
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
}
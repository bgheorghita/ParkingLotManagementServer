package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementWeb.databases.MongoDbHelper;
import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.ParkingSpotPrice;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class ParkingSpotTypePriceDaoImplIT {
    @Autowired
    private MongoDbHelper mongoDbHelper;
    private ParkingSpotTypePriceDaoImpl parkingSpotTypePriceDaoImpl;

    @BeforeEach
    void setUp() {
        parkingSpotTypePriceDaoImpl = new ParkingSpotTypePriceDaoImpl(mongoDbHelper);
    }

    @Test
    void save_ShouldSaveAPriceForParkingSpotTypeOnlyWhenThereIsNotAlreadyAPriceForThatParkingSpotTypeSaved(){
        Price price = new Price(25, Currency.EUR);
        ParkingSpotType parkingSpotType = ParkingSpotType.SMALL;
        ParkingSpotPrice parkingSpotPrice = new ParkingSpotPrice(parkingSpotType, price);

        Optional<Price> parkingSpotPriceOptional = parkingSpotTypePriceDaoImpl.findByParkingSpotType(parkingSpotType);
        boolean saved = parkingSpotTypePriceDaoImpl.save(parkingSpotPrice);

        if(parkingSpotPriceOptional.isPresent()){
            assertFalse(saved);
        } else {
            assertTrue(saved);
            cleanUp(parkingSpotType);
        }
    }

    @Test
    void findByParkingSpotType_ShouldReturnOptionalOfPriceWhenSearchedParkingSpotTypeIsFound(){
        Price searchedPrice = new Price(10, Currency.RON);
        ParkingSpotType parkingSpotType = ParkingSpotType.SMALL;
        ParkingSpotPrice parkingSpotPrice = new ParkingSpotPrice(parkingSpotType, searchedPrice);

        boolean saved = parkingSpotTypePriceDaoImpl.save(parkingSpotPrice);
        Optional<Price> resultPrice = parkingSpotTypePriceDaoImpl.findByParkingSpotType(parkingSpotType);

        assertTrue(resultPrice.isPresent());

        if(saved){
            assertEquals(searchedPrice.toString(), resultPrice.get().toString());
            cleanUp(parkingSpotType);
        }
    }

    @Test
    void deleteByParkingSpotType_ShouldDeleteAParkingSpotPriceWhenSearchedParkingSpotTypeIsFound(){
        Price searchedPrice = new Price(10, Currency.RON);
        ParkingSpotType parkingSpotType = ParkingSpotType.SMALL;
        ParkingSpotPrice parkingSpotPrice = new ParkingSpotPrice(parkingSpotType, searchedPrice);

        Optional<Price> parkingSpotPriceOptional = parkingSpotTypePriceDaoImpl.findByParkingSpotType(parkingSpotType);
        long deletedCount;

        if(parkingSpotPriceOptional.isPresent()){
            deletedCount = parkingSpotTypePriceDaoImpl.deleteByParkingSpotType(parkingSpotType);
            assertEquals(1, deletedCount);
            // save back
            parkingSpotTypePriceDaoImpl.save(new ParkingSpotPrice(parkingSpotType, parkingSpotPriceOptional.get()));
        } else {
            parkingSpotTypePriceDaoImpl.save(parkingSpotPrice);
            deletedCount = parkingSpotTypePriceDaoImpl.deleteByParkingSpotType(parkingSpotType);
            assertEquals(1, deletedCount);
        }
    }

    private void cleanUp(ParkingSpotType parkingSpotType){
        parkingSpotTypePriceDaoImpl.deleteByParkingSpotType(parkingSpotType);
    }

}
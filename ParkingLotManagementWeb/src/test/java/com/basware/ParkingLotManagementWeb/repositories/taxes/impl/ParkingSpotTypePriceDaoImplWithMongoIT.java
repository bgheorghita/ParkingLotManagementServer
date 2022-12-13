package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementWeb.databases.MongoDB;
import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.ParkingSpotPrice;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementWeb.repositories.taxes.impl.ParkingSpotTypePriceDaoImplWithMongo;
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
class ParkingSpotTypePriceDaoImplWithMongoIT {
    @Autowired
    private MongoDB mongoDB;
    private ParkingSpotTypePriceDaoImplWithMongo parkingSpotTypePriceDaoImplWithMongo;

    @BeforeEach
    void setUp() {
        parkingSpotTypePriceDaoImplWithMongo = new ParkingSpotTypePriceDaoImplWithMongo(mongoDB);
    }

    @Test
    void save_ShouldSaveAPriceForParkingSpotTypeOnlyWhenThereIsNotAlreadyAPriceForThatParkingSpotTypeSaved(){
        Price price = new Price(25, Currency.EUR);
        ParkingSpotType parkingSpotType = ParkingSpotType.SMALL;
        ParkingSpotPrice parkingSpotPrice = new ParkingSpotPrice(parkingSpotType, price);

        Optional<Price> parkingSpotPriceOptional = parkingSpotTypePriceDaoImplWithMongo.findByParkingSpotType(parkingSpotType);
        boolean saved = parkingSpotTypePriceDaoImplWithMongo.save(parkingSpotPrice);

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

        boolean saved = parkingSpotTypePriceDaoImplWithMongo.save(parkingSpotPrice);
        Optional<Price> resultPrice = parkingSpotTypePriceDaoImplWithMongo.findByParkingSpotType(parkingSpotType);

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

        Optional<Price> parkingSpotPriceOptional = parkingSpotTypePriceDaoImplWithMongo.findByParkingSpotType(parkingSpotType);
        long deletedCount;

        if(parkingSpotPriceOptional.isPresent()){
            deletedCount = parkingSpotTypePriceDaoImplWithMongo.deleteByParkingSpotType(parkingSpotType);
            assertEquals(1, deletedCount);
            // save back
            parkingSpotTypePriceDaoImplWithMongo.save(new ParkingSpotPrice(parkingSpotType, parkingSpotPriceOptional.get()));
        } else {
            parkingSpotTypePriceDaoImplWithMongo.save(parkingSpotPrice);
            deletedCount = parkingSpotTypePriceDaoImplWithMongo.deleteByParkingSpotType(parkingSpotType);
            assertEquals(1, deletedCount);
        }
    }

    private void cleanUp(ParkingSpotType parkingSpotType){
        parkingSpotTypePriceDaoImplWithMongo.deleteByParkingSpotType(parkingSpotType);
    }

}
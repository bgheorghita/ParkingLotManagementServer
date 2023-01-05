package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementCommon.models.taxes.TypePrice;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.utils.databases.MongoDbHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles="test")
class TypePriceDaoImplIT {

    @Autowired
    private MongoDbHelper mongoDbHelper;
    private TypePriceDaoImplExtended typePriceDao;

    @BeforeEach
    void setUp(){
        typePriceDao = new TypePriceDaoImplExtended(mongoDbHelper);
        typePriceDao.deleteAll();
    }

    @Test
    void saveUnique_ShouldSaveTypePriceIntoDatabaseCollection(){
        TypeInfo typeInfo = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());
        Price price = new Price(1, Currency.EUR);
        TypePrice typePrice = new TypePrice(typeInfo, price);

        boolean saved = typePriceDao.saveUnique(typePrice);
        assertTrue(saved);
    }

    @Test
    void saveUnique_ShouldNotSaveTypePriceWhenTheTypeInfoAlreadyExistsIntoDatabase(){
        TypeInfo typeInfo = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());
        Price price = new Price(1, Currency.EUR);
        TypePrice typePrice1 = new TypePrice(typeInfo, price);
        TypePrice typePrice2 = new TypePrice(typeInfo, price);

        boolean saved1 = typePriceDao.saveUnique(typePrice1);
        boolean saved2 = typePriceDao.saveUnique(typePrice2);
        assertTrue(saved1);
        assertFalse(saved2);
    }

    @Test
    void deleteAll_ShouldDeleteAllDataFromDatabaseCollection(){
        TypeInfo typeInfo1 = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());
        Price price1 = new Price(1, Currency.EUR);
        TypePrice typePrice1 = new TypePrice(typeInfo1, price1);

        TypeInfo typeInfo2 = new TypeInfo(TypeInfo.USER_IDENTIFIER, UserType.REGULAR.name());
        Price price2 = new Price(5, Currency.RON);
        TypePrice typePrice2 = new TypePrice(typeInfo2, price2);

        typePriceDao.saveUnique(typePrice1);
        typePriceDao.saveUnique(typePrice2);
        typePriceDao.deleteAll();

        assertEquals(0, typePriceDao.getSize());
    }

    @Test
    void getPriceByTypeInfo_ShouldReturnPriceAccordingToTheTypeInfoWhenItExistsIntoDatabase(){
        TypeInfo typeInfo = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());
        Price price = new Price(1, Currency.EUR);
        TypePrice typePrice = new TypePrice(typeInfo, price);
        typePriceDao.saveUnique(typePrice);

        Optional<Price> priceOptional = typePriceDao.getPriceByTypeInfo(typeInfo);

        assertTrue(priceOptional.isPresent());
        assertEquals(price.toString(), priceOptional.get().toString());
    }

    @Test
    void getPriceByTypeInfo_ShouldReturnEmptyOptionalWhenPriceForTypeInfoDoesNotExistIntoDatabase(){
        TypeInfo typeInfo = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());

        Optional<Price> priceOptional = typePriceDao.getPriceByTypeInfo(typeInfo);

        assertTrue(priceOptional.isEmpty());
    }

}

class TypePriceDaoImplExtended extends TypePriceDaoImpl{

    private final MongoDbHelper mongoDbHelper;

    public TypePriceDaoImplExtended(MongoDbHelper mongoDbHelper) {
        super(mongoDbHelper);
        this.mongoDbHelper = mongoDbHelper;
    }

    public long getSize(){
        return mongoDbHelper.getMongoCollection(MongoDbHelper.PRICES_COLLECTION)
                .countDocuments();
    }
}
package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.TypeInfo;
import com.basware.ParkingLotManagementCommon.models.taxes.TypePrice;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.repositories.taxes.morphiaImpl.TypePriceDaoMorphiaImpl;
import dev.morphia.Datastore;
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
    private Datastore datastore;
    private TypePriceDaoMorphiaExtended typePriceDaoMorphiaImpl;

    @BeforeEach
    void setUp(){
        typePriceDaoMorphiaImpl = new TypePriceDaoMorphiaExtended(datastore);
        typePriceDaoMorphiaImpl.deleteAll();
    }

    @Test
    void save_ShouldSaveTypePriceIntoDatabaseCollection(){
        TypeInfo typeInfo = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());
        Price price = new Price(1, Currency.EUR);
        TypePrice typePrice = new TypePrice(typeInfo, price);

        boolean saved = typePriceDaoMorphiaImpl.save(typePrice);
        assertTrue(saved);
    }

    @Test
    void save_ShouldNotSaveTypePriceWhenTheTypeInfoAlreadyExistsIntoDatabase(){
        TypeInfo typeInfo = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());
        Price price = new Price(1, Currency.EUR);
        TypePrice typePrice1 = new TypePrice(typeInfo, price);
        TypePrice typePrice2 = new TypePrice(typeInfo, price);

        boolean saved1 = typePriceDaoMorphiaImpl.save(typePrice1);
        boolean saved2 = typePriceDaoMorphiaImpl.save(typePrice2);
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

        typePriceDaoMorphiaImpl.save(typePrice1);
        typePriceDaoMorphiaImpl.save(typePrice2);
        typePriceDaoMorphiaImpl.deleteAll();

        assertEquals(0, typePriceDaoMorphiaImpl.getSize());
    }

    @Test
    void getPriceByTypeInfo_ShouldReturnPriceAccordingToTheTypeInfoWhenItExistsIntoDatabase(){
        TypeInfo typeInfo = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());
        Price price = new Price(1, Currency.EUR);
        TypePrice typePrice = new TypePrice(typeInfo, price);
        typePriceDaoMorphiaImpl.save(typePrice);

        Optional<Price> priceOptional = typePriceDaoMorphiaImpl.getPriceByTypeInfo(typeInfo);

        assertTrue(priceOptional.isPresent());
        assertEquals(price.toString(), priceOptional.get().toString());
    }

    @Test
    void getPriceByTypeInfo_ShouldReturnEmptyOptionalWhenPriceForTypeInfoDoesNotExistIntoDatabase(){
        TypeInfo typeInfo = new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name());

        Optional<Price> priceOptional = typePriceDaoMorphiaImpl.getPriceByTypeInfo(typeInfo);

        assertTrue(priceOptional.isEmpty());
    }

}
class TypePriceDaoMorphiaExtended extends TypePriceDaoMorphiaImpl{

    private final Datastore datastore;

    public TypePriceDaoMorphiaExtended(Datastore datastore){
        super(datastore);
        this.datastore = datastore;
    }

    public long getSize(){
        return datastore.find(TypePrice.class).count();
    }
}
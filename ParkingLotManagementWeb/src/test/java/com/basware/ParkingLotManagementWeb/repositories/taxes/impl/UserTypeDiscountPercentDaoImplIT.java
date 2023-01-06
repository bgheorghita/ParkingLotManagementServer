package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.repositories.taxes.morphiaImpl.UserTypeDiscountDaoMorphiaImpl;
import dev.morphia.Datastore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles(profiles = "test")
class UserTypeDiscountPercentDaoImplIT {
    @Autowired
    private Datastore datastore;

    private UserTypeDiscountDaoMorphiaExtended userTypeDiscountDaoMorphiaImpl;

    @BeforeEach
    void setUp() {
        userTypeDiscountDaoMorphiaImpl = new UserTypeDiscountDaoMorphiaExtended(datastore);
        userTypeDiscountDaoMorphiaImpl.deleteAll();
    }

    @Test
    void save_ShouldSaveUserDiscountIntoDatabase(){
        UserDiscount userDiscount = new UserDiscount(UserType.REGULAR, 0.25);

        boolean saved = userTypeDiscountDaoMorphiaImpl.save(userDiscount);

        assertTrue(saved);
    }

    @Test
    void save_ShouldNotSaveUserDiscountWhenUserTypeIsAlreadySavedIntoDatabase(){
        UserType userType = UserType.REGULAR;
        UserDiscount userDiscount1 = new UserDiscount(userType, 0.25);
        UserDiscount userDiscount2 = new UserDiscount(userType, 0.30);

        boolean saved1 = userTypeDiscountDaoMorphiaImpl.save(userDiscount1);
        boolean saved2 = userTypeDiscountDaoMorphiaImpl.save(userDiscount2);

        assertTrue(saved1);
        assertFalse(saved2);
    }

    @Test
    void deleteAll_ShouldDeleteAllDataFromDatabaseCollection(){
        UserDiscount userDiscount1 = new UserDiscount(UserType.REGULAR, 0.25);
        UserDiscount userDiscount2 = new UserDiscount(UserType.VIP, 0.50);

        userTypeDiscountDaoMorphiaImpl.save(userDiscount1);
        userTypeDiscountDaoMorphiaImpl.save(userDiscount2);

        userTypeDiscountDaoMorphiaImpl.deleteAll();

        assertEquals(0, userTypeDiscountDaoMorphiaImpl.getSize());
    }

    @Test
    void findByUserType_ShouldReturnDiscountForUserTypeWhenItExistsIntoDatabase(){
        UserType userType = UserType.REGULAR;
        double discount = 0.25;
        UserDiscount userDiscount = new UserDiscount(userType, discount);

        userTypeDiscountDaoMorphiaImpl.save(userDiscount);

        Optional<Double> discountOptional = userTypeDiscountDaoMorphiaImpl.findByUserType(userType);
        assertTrue(discountOptional.isPresent());
        assertEquals(discount, discountOptional.get());
    }

    @Test
    void findByUserType_ShouldReturnEmptyOptionalWhenDiscountForUserTypeDoesNotExistIntoDatabase(){
        UserType userType = UserType.REGULAR;
        Optional<Double> discountOptional = userTypeDiscountDaoMorphiaImpl.findByUserType(userType);
        assertTrue(discountOptional.isEmpty());
    }

}

class UserTypeDiscountDaoMorphiaExtended extends UserTypeDiscountDaoMorphiaImpl{

    private final Datastore datastore;

    public UserTypeDiscountDaoMorphiaExtended(Datastore datastore) {
        super(datastore);
        this.datastore = datastore;
    }

    public long getSize(){
        return datastore.find(UserDiscount.class).count();
    }
}
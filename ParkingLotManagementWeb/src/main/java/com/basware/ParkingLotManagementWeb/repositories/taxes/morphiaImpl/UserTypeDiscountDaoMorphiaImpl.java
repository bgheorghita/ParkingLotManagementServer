package com.basware.ParkingLotManagementWeb.repositories.taxes.morphiaImpl;

import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.repositories.taxes.UserTypeDiscountPercentDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoWriteException;
import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.query.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.morphia.query.experimental.filters.Filters.eq;

@Service
@Primary
public class UserTypeDiscountDaoMorphiaImpl implements UserTypeDiscountPercentDao {

    private final Datastore datastore;

    public UserTypeDiscountDaoMorphiaImpl(Datastore datastore){
        this.datastore = datastore;
    }
    @Override
    public Optional<Double> findByUserType(UserType userType) {
        Query<UserDiscount> query = datastore.find(UserDiscount.class);
        UserDiscount userDiscount = query
                .filter(eq(UserDiscount.USER_TYPE_FIELD, userType))
                .first();

        return userDiscount == null ? Optional.empty() : Optional.of(userDiscount.getPercent());
    }

    @Override
    public boolean save(UserDiscount userDiscount) {
        try{
            return datastore.save(userDiscount) != null;
        } catch (MongoWriteException e){
            return false;
        }
    }

    @Override
    public long deleteAll() {
        return datastore.find(UserDiscount.class).delete(new DeleteOptions().multi(true)).getDeletedCount();
    }
}

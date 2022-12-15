package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.databases.MongoDbHelper;
import com.basware.ParkingLotManagementWeb.repositories.taxes.UserTypeDiscountPercentDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Component
public class UserTypeDiscountPercentDaoImpl implements UserTypeDiscountPercentDao {
    private final MongoDbHelper mongoDbHelper;

    public UserTypeDiscountPercentDaoImpl(final MongoDbHelper mongoDbHelper){
        this.mongoDbHelper = mongoDbHelper;
    }

    @Override
    public Optional<Double> findByUserType(UserType userType) {
        Document doc = getCollectionFromDatabase()
                .find(eq(UserDiscount.USER_TYPE_FIELD, userType))
                .first();
        if(doc == null) return Optional.empty();

        String json = doc.toJson();
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            double percent = jsonNode.get(UserDiscount.PERCENT_FIELD).asDouble();
            return Optional.of(percent);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean save(UserDiscount userDiscount) {
        try {
            String object = new ObjectMapper().writeValueAsString(userDiscount);
            Document doc = Document.parse(object);
            getCollectionFromDatabase().insertOne(doc);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    @Override
    public long deleteAll() {
        return getCollectionFromDatabase().deleteMany(new Document()).getDeletedCount();
    }

    private MongoCollection<Document> getCollectionFromDatabase(){
        return mongoDbHelper.getMongoCollection(MongoDbHelper.USER_TYPE_DISCOUNT_COLLECTION);
    }
}

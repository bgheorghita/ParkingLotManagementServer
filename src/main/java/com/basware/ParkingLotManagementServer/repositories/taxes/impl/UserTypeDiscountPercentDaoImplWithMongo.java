package com.basware.ParkingLotManagementServer.repositories.taxes.impl;

import com.basware.ParkingLotManagementServer.databases.MongoDb;
import com.basware.ParkingLotManagementServer.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.repositories.taxes.UserTypeDiscountPercentDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Component
public class UserTypeDiscountPercentDaoImplWithMongo implements UserTypeDiscountPercentDao {
    private final MongoDb mongoDb;

    public UserTypeDiscountPercentDaoImplWithMongo(final MongoDb mongoDb){
        this.mongoDb = mongoDb;
    }

    @Override
    public Optional<Double> findByUserType(UserType userType) {
        Document doc = getCollectionFromDatabase().find(eq("userType", userType)).first();
        if(doc == null) return Optional.empty();

        String json = doc.toJson();
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            double percent = jsonNode.get("percent").asDouble();
            return Optional.of(percent);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(UserDiscount userDiscount) {
        try {
            String object = new ObjectMapper().writeValueAsString(userDiscount);
            Document doc = Document.parse(object);
            getCollectionFromDatabase().insertOne(doc);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        getCollectionFromDatabase().deleteMany(new Document());
    }

    private MongoCollection<Document> getCollectionFromDatabase(){
        String dbName = mongoDb.getDatabaseProperties().getDatabaseName();
        MongoDatabase database = mongoDb.getDbConnection().getDatabase(dbName);
        return database.getCollection(MongoDb.USER_TYPE_DISCOUNT_PRICE_COLLECTION);
    }
}
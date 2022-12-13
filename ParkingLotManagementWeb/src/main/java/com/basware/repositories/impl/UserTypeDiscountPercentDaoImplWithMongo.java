package com.basware.repositories.impl;

import com.basware.models.taxes.discounts.UserDiscount;
import com.basware.models.users.UserType;
import com.basware.databases.MongoDB;
import com.basware.repositories.UserTypeDiscountPercentDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Component
public class UserTypeDiscountPercentDaoImplWithMongo implements UserTypeDiscountPercentDao {
    private final MongoDB mongoDB;

    public UserTypeDiscountPercentDaoImplWithMongo(final MongoDB mongoDB){
        this.mongoDB = mongoDB;
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
        String dbName = mongoDB.getDatabaseProperties().getDatabaseName();
        com.mongodb.client.MongoDatabase database = mongoDB.getDatabaseConnection().getDatabase(dbName);
        return database.getCollection(MongoDB.USER_TYPE_DISCOUNT_PRICE_COLLECTION);
    }
}

package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementWeb.databases.MongoDbHelper;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.taxes.UserPrice;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementWeb.repositories.taxes.UserTypePriceDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Component
public class UserTypePriceDaoImpl implements UserTypePriceDao {
    private final MongoDbHelper mongoDbHelper;

    public UserTypePriceDaoImpl(final MongoDbHelper mongoDbHelper){
        this.mongoDbHelper = mongoDbHelper;
    }

    @Override
    public Optional<Price> findByUserType(UserType userType) {
        Document doc = getCollectionFromDatabase()
                .find(eq(MongoDbHelper.USER_TYPE_DATABASE_FIELD_NAME, userType))
                .first();
        if(doc == null) return Optional.empty();

        String json = doc.toJson();
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            JsonNode priceNode = jsonNode.get("price");
            double units = priceNode.get("units").asDouble();
            String currency = priceNode.get("currency").asText();
            return Optional.of(new Price(units, Currency.valueOf(currency)));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(UserPrice userPrice) {
        try {
            String object = new ObjectMapper().writeValueAsString(userPrice);
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
        return mongoDbHelper.getMongoCollection(MongoDbHelper.USER_TYPE_PRICE_COLLECTION);
    }
}

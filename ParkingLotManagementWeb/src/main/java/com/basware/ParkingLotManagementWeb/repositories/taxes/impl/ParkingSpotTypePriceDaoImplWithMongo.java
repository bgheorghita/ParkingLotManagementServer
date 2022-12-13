package com.basware.ParkingLotManagementWeb.repositories.taxes.impl;

import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.Currency;
import com.basware.ParkingLotManagementCommon.models.taxes.ParkingSpotPrice;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementWeb.databases.MongoDB;
import com.basware.ParkingLotManagementWeb.repositories.taxes.ParkingSpotTypePriceDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Service
public class ParkingSpotTypePriceDaoImplWithMongo implements ParkingSpotTypePriceDao {

    private final MongoDB mongoDB;
    public ParkingSpotTypePriceDaoImplWithMongo(MongoDB mongoDB){
        this.mongoDB = mongoDB;
        createIndex();
    }

    private void createIndex() {
        IndexOptions uniqueIndex = new IndexOptions();
        uniqueIndex.unique(true);
        Document index = new Document(MongoDB.PARKING_SPOT_TYPE_INDEX, 1);
        try{
            getCollectionFromDatabase().createIndex(index, uniqueIndex);
        } catch (MongoCommandException| MongoWriteException e){
            System.out.println(e.getMessage());
            System.out.println("Index " + index.toJson() + " could not be created.");
        }
    }

    @Override
    public Optional<Price> findByParkingSpotType(ParkingSpotType parkingSpotType) {
        Document doc = getCollectionFromDatabase().find(eq("parkingSpotType", parkingSpotType)).first();
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
    public boolean save(ParkingSpotPrice parkingSpotPrice) {
        try {
            String object = new ObjectMapper().writeValueAsString(parkingSpotPrice);
            Document doc = Document.parse(object);
            getCollectionFromDatabase().insertOne(doc);
            return true;
        } catch (JsonProcessingException | MongoWriteException e) {
            return false;
        }
    }

    @Override
    public long deleteByParkingSpotType(ParkingSpotType parkingSpotType) {
        Document document = getCollectionFromDatabase()
                .find(eq("parkingSpotType", parkingSpotType))
                .first();
        return document == null ? 0 : getCollectionFromDatabase().deleteOne(document).getDeletedCount();
    }

    @Override
    public void deleteAll() {
        getCollectionFromDatabase().deleteMany(new Document());
    }

    private MongoCollection<Document> getCollectionFromDatabase(){
        String dbName = mongoDB.getDatabaseProperties().getDatabaseName();
        MongoDatabase database = mongoDB.getDatabaseConnection().getDatabase(dbName);
        return database.getCollection(MongoDB.PARKING_SPOT_PRICE_COLLECTION);
    }
}

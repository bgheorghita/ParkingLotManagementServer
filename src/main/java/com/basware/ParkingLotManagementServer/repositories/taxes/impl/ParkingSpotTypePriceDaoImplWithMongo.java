package com.basware.ParkingLotManagementServer.repositories.taxes.impl;

import com.basware.ParkingLotManagementServer.databases.MongoDb;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.ParkingSpotPrice;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.repositories.taxes.ParkingSpotTypePriceDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Service
public class ParkingSpotTypePriceDaoImplWithMongo implements ParkingSpotTypePriceDao {
    private final MongoDb mongoDb;

    public ParkingSpotTypePriceDaoImplWithMongo(final MongoDb mongoDb){
        this.mongoDb = mongoDb;
    }

    @Override
    public Optional<ParkingSpotPrice> findByParkingSpotType(ParkingSpotType parkingSpotType) {
        Document doc = getCollectionFromDatabase().find(eq("parkingSpotType", parkingSpotType)).first();
        if(doc == null) return Optional.empty();

        String json = doc.toJson();
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            JsonNode priceNode = jsonNode.get("price");
            double units = priceNode.get("units").asDouble();
            String currency = priceNode.get("currency").asText();

            Price price = new Price(units, Currency.valueOf(currency));
            ParkingSpotPrice parkingSpotPrice = new ParkingSpotPrice(parkingSpotType, price);
            return Optional.of(parkingSpotPrice);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(ParkingSpotPrice parkingSpotPrice) {
        try {
            String object = new ObjectMapper().writeValueAsString(parkingSpotPrice);
            Document doc = Document.parse(object);
            getCollectionFromDatabase().insertOne(doc);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(ParkingSpotPrice parkingSpotPrice) {
        try {
            String object = new ObjectMapper().writeValueAsString(parkingSpotPrice);
            Document doc = Document.parse(object);
            getCollectionFromDatabase().deleteOne(doc);
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
        return database.getCollection(MongoDb.PARKING_SPOT_PRICE_COLLECTION);
    }
}

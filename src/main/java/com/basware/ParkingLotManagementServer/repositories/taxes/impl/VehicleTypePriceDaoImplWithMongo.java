package com.basware.ParkingLotManagementServer.repositories.taxes.impl;

import com.basware.ParkingLotManagementServer.databases.MongoDb;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.taxes.VehiclePrice;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.repositories.taxes.VehicleTypePriceDao;
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
public class VehicleTypePriceDaoImplWithMongo implements VehicleTypePriceDao {
    private final MongoDb mongoDb;

    public VehicleTypePriceDaoImplWithMongo(final MongoDb mongoDb){
        this.mongoDb = mongoDb;
    }

    @Override
    public Optional<Price> findByVehicleType(VehicleType vehicleType) {
        Document doc = getCollectionFromDatabase().find(eq("vehicleType", vehicleType)).first();
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
    public void save(VehiclePrice vehiclePrice) {
        try {
            String object = new ObjectMapper().writeValueAsString(vehiclePrice);
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
        return database.getCollection(MongoDb.VEHICLE_PRICE_COLLECTION);
    }
}

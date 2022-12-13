package com.basware.repositories.impl;

import com.basware.databases.MongoDB;
import com.basware.models.taxes.Currency;
import com.basware.models.taxes.Price;
import com.basware.models.taxes.VehiclePrice;
import com.basware.models.vehicles.VehicleType;
import com.basware.repositories.VehicleTypePriceDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

@Component
public class VehicleTypePriceDaoImplWithMongo implements VehicleTypePriceDao {
    private final MongoDB mongoDB;

    public VehicleTypePriceDaoImplWithMongo(final MongoDB mongoDB){
        this.mongoDB = mongoDB;
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
        String dbName = mongoDB.getDatabaseProperties().getDatabaseName();
        com.mongodb.client.MongoDatabase database = mongoDB.getDatabaseConnection().getDatabase(dbName);
        return database.getCollection(MongoDB.VEHICLE_PRICE_COLLECTION);
    }
}

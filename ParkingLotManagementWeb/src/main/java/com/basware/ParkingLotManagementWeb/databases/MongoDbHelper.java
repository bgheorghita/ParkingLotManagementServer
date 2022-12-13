package com.basware.ParkingLotManagementWeb.databases;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class MongoDbHelper {
    private final DatabaseConfiguration databaseConfiguration;
    public static final String PARKING_SPOT_TYPE_PRICE_COLLECTION = "parkingSpotTypePrices";
    public static final String USER_TYPE_PRICE_COLLECTION = "userTypePrices";
    public static final String VEHICLE_TYPE_PRICE_COLLECTION = "vehicleTypePrices";
    public static final String USER_TYPE_DISCOUNT_PRICE_COLLECTION = "userDiscounts";
    public static final String PARKING_SPOT_TYPE_INDEX = "parkingSpotType";
    public static final String PARKING_SPOT_TYPE_DATABASE_FIELD_NAME = "parkingSpotType";
    public static final String USER_TYPE_DATABASE_FIELD_NAME = "userType";
    public static final String VEHICLE_TYPE_DATABASE_FIELD_NAME = "vehicleType";
    public static final String USER_TYPE_DISCOUNT_DATABASE_FIELD_NAME = USER_TYPE_DATABASE_FIELD_NAME;
    private static MongoClient mongoClient = null;

    public MongoDbHelper(DatabaseConfiguration databaseConfiguration) {
        this.databaseConfiguration = databaseConfiguration;
        if(mongoClient == null){
            mongoClient = MongoClients.create(databaseConfiguration.getConnectionString());
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
    public DatabaseConfiguration getDatabaseProperties(){
        return databaseConfiguration;
    }

    public MongoCollection<Document> getMongoCollection(String collectionName){
        return mongoClient
                .getDatabase(databaseConfiguration.getDatabaseName())
                .getCollection(collectionName);
    }
}
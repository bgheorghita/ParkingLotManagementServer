package com.basware.ParkingLotManagementWeb.utils.helpers;

import com.basware.ParkingLotManagementWeb.configs.DatabaseConfiguration;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class MongoDbHelper {
    private final DatabaseConfiguration databaseConfiguration;
    public static final String USER_TYPE_DISCOUNT_COLLECTION = "discounts";
    public static final String PRICES_COLLECTION = "prices";

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
    public DatabaseConfiguration getDatabaseConfiguration(){
        return databaseConfiguration;
    }

    public MongoCollection<Document> getMongoCollection(String collectionName){
        return mongoClient
                .getDatabase(databaseConfiguration.getDatabaseName())
                .getCollection(collectionName);
    }
}
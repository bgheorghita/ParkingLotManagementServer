package com.basware.ParkingLotManagementServer.databases;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.stereotype.Component;

@Component
public class MongoDB {
    private final DatabaseProperties databaseProperties;
    public static final String PARKING_SPOT_PRICE_COLLECTION = "parkingSpotPrice";
    public static final String USER_PRICE_COLLECTION = "userPrice";
    public static final String VEHICLE_PRICE_COLLECTION = "vehiclePrice";
    public static final String USER_TYPE_DISCOUNT_PRICE_COLLECTION = "userDiscount";
    public static final String PARKING_SPOT_TYPE_INDEX = "parkingSpotType";
    private static MongoClient databaseConnection = null;

    public MongoDB(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public MongoClient getDatabaseConnection() {
        if(databaseConnection == null){
            databaseConnection = MongoClients.create(databaseProperties.getConnectionString());
        }
        return databaseConnection;
    }
    public DatabaseProperties getDatabaseProperties(){
        return databaseProperties;
    }
}
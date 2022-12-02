package com.basware.ParkingLotManagementServer.databases;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

@Component
public class MongoDb {
    private final DatabaseProperties databaseProperties;
    public static final String PARKING_SPOT_PRICE_COLLECTION = "parkingSpotPrice";
    public static final String USER_PRICE_COLLECTION = "userPrice";
    public static final String VEHICLE_PRICE_COLLECTION = "vehiclePrice";
    public static final String USER_TYPE_DISCOUNT_PRICE_COLLECTION = "userDiscount";
    private static MongoClient databaseConnection = null;

    public MongoDb(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
        if(databaseConnection == null){
            databaseConnection = MongoClients.create(databaseProperties.getConnectionString());
        }
    }

    public void closeConnection(){
        databaseConnection.close();
    }

    public MongoClient getDbConnection() {
        return databaseConnection;
    }

    public MongoDatabase createDatabase(String dbName){
        return databaseConnection.getDatabase(dbName);
    }

    public void createCollection(String databaseName, String collectionName){
        databaseConnection.getDatabase(databaseName).createCollection(collectionName);
    }

    public void printDatabasesName(){
        Iterable<String> names = databaseConnection.listDatabaseNames();
        names.forEach(System.out::println);
    }

    public DatabaseProperties getDatabaseProperties(){
        return databaseProperties;
    }

    public void deleteDatabase(String databaseName){
        databaseConnection.getDatabase(databaseName).drop();
    }
}

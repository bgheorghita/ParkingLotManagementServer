package com.basware.ParkingLotManagementServer.databases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@Configuration
public class DatabaseProperties {
    private final String databaseName;
    private final String connectionString;

    public DatabaseProperties(@Value("${database.name}")String databaseName,
                              @Value("${database.connection}") String connectionString) {
        this.databaseName = databaseName;
        this.connectionString = connectionString;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getConnectionString() {
        return connectionString;
    }

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}

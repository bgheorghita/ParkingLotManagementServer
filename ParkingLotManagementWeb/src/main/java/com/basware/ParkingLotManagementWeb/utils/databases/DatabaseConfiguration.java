package com.basware.ParkingLotManagementWeb.utils.databases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {
    private final String databaseName;
    private final String connectionString;

    public DatabaseConfiguration(@Value("${database.name}") String databaseName,
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
}
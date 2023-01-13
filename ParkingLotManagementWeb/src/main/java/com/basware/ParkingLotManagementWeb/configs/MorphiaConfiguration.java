package com.basware.ParkingLotManagementWeb.configs;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MorphiaConfiguration {

    public static final String TAXES_MODELS_PACKAGE = "com.basware.ParkingLotManagementCommon.models.taxes";
    public static final String DISCOUNT_MODELS_PACKAGE = "com.basware.ParkingLotManagementCommon.models.taxes.discounts";
    public static final String USERS_MODELS_PACKAGE = "com.basware.ParkingLotManagementCommon.models.users";
    public static final String VEHICLES_MODELS_PACKAGE = "com.basware.ParkingLotManagementCommon.models.vehicles";
    public static final String PARKING_SPOTS_MODELS_PACKAGE = "com.basware.ParkingLotManagementCommon.models.parking.spots";
    public static final String TICKET_MODEL_PACKAGE = "com.basware.ParkingLotManagementCommon.models.tickets";

    private final DatabaseConfiguration databaseConfiguration;

    public MorphiaConfiguration(DatabaseConfiguration databaseConfiguration){
        this.databaseConfiguration = databaseConfiguration;
    }

    @Bean
    public Datastore datastore() {
        final Datastore datastore = Morphia.createDatastore(MongoClients.create(databaseConfiguration.getConnectionString()),
                databaseConfiguration.getDatabaseName());
        datastore.getMapper().mapPackage(TAXES_MODELS_PACKAGE);
        datastore.getMapper().mapPackage(DISCOUNT_MODELS_PACKAGE);
        datastore.getMapper().mapPackage(USERS_MODELS_PACKAGE);
        datastore.getMapper().mapPackage(VEHICLES_MODELS_PACKAGE);
        datastore.getMapper().mapPackage(PARKING_SPOTS_MODELS_PACKAGE);
        datastore.getMapper().mapPackage(TICKET_MODEL_PACKAGE);
        datastore.ensureIndexes();
        return datastore;
    }
}

package com.basware.ParkingLotManagementServer.repositories.taxes.impl;

import com.basware.ParkingLotManagementServer.databases.MongoDb;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.ParkingSpotPrice;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class ParkingSpotTypePriceDaoImplWithMongoIT {

    @Autowired
    private MongoDb mongoDb;

    private ParkingSpotTypePriceDaoImplWithMongoForTest parkingSpotTypePriceDaoImplWithMongo;

    @BeforeEach
    void setUp() {
        parkingSpotTypePriceDaoImplWithMongo = new ParkingSpotTypePriceDaoImplWithMongoForTest(mongoDb);
    }

    @Test
    @Transactional
    void saveOneDocumentShouldIncreaseSizeOfCollectionByOne(){
        Price price = new Price(25, Currency.EUR);
        ParkingSpotPrice parkingSpotPrice = new ParkingSpotPrice(ParkingSpotType.SMALL, price);
        int sizeBeforeSave = parkingSpotTypePriceDaoImplWithMongo.getCollectionSize();
        parkingSpotTypePriceDaoImplWithMongo.save(parkingSpotPrice);
        int sizeAfterSave = parkingSpotTypePriceDaoImplWithMongo.getCollectionSize();

        assertEquals(sizeBeforeSave + 1, sizeAfterSave);
        parkingSpotTypePriceDaoImplWithMongo.delete(parkingSpotPrice);
    }
}

class ParkingSpotTypePriceDaoImplWithMongoForTest extends ParkingSpotTypePriceDaoImplWithMongo{

    private final MongoDb mongoDb;

    public ParkingSpotTypePriceDaoImplWithMongoForTest(MongoDb mongoDb) {
        super(mongoDb);
        this.mongoDb = mongoDb;
    }

    public int getCollectionSize(){
        MongoCollection<Document> coll = mongoDb.getDbConnection()
                .getDatabase(mongoDb.getDatabaseProperties().getDatabaseName())
                .getCollection(MongoDb.PARKING_SPOT_PRICE_COLLECTION);
        int size = 0;

        try (MongoCursor<Document> cursor = coll.find().iterator()) {
            while (cursor.hasNext()) {
                size++;
                System.out.println("document is " +cursor.next() );
            }
        }
        return size;
    }
}
package com.basware.ParkingLotManagementServer;

import com.mongodb.client.internal.MongoClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ParkingLotManagementServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingLotManagementServerApplication.class, args);
	}

}

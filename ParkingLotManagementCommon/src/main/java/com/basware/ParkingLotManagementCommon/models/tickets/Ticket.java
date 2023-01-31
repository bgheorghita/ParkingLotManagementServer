package com.basware.ParkingLotManagementCommon.models.tickets;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Entity("tickets")
public class Ticket {
	public static final String VEHICLE_PLATE_NUMBER_FIELD = "vehiclePlateNumber";
	@Id
	private ObjectId objectId;

	private String userName;

	@Property(VEHICLE_PLATE_NUMBER_FIELD)
	private String vehiclePlateNumber;
	private long parkingSpotNumber;

	@Property("startTime")
	private LocalDateTime startTime;


	public Ticket(){}

	public Ticket(String userName, String vehiclePlateNumber, long parkingSpotNumber) {
		this.userName = userName;
		this.vehiclePlateNumber = vehiclePlateNumber;
		this.parkingSpotNumber = parkingSpotNumber;
		startTime = LocalDateTime.now();
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public String getUserName() {
		return userName;
	}

	public String getVehiclePlateNumber() {
		return vehiclePlateNumber;
	}
	public long getParkingSpotNumber() {
		return parkingSpotNumber;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"objectId=" + objectId +
				", userName='" + userName + '\'' +
				", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
				", parkingSpotNumber=" + parkingSpotNumber +
				", startTime=" + startTime +
				'}';
	}
}

package com.basware.ParkingLotManagementCommon.models.tickets;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Entity("tickets")
public class Ticket {
	@Id
	private ObjectId objectId;

	@Reference(ignoreMissing = true)
	private User user;

	@Reference(ignoreMissing = true)
	private Vehicle vehicle;

	@Reference(ignoreMissing = true)
	private ParkingSpot parkingSpot;

	@Property("startTime")
	private LocalDateTime startTime;


	public Ticket(){}

	public Ticket(User user, Vehicle vehicle, ParkingSpot parkingSpot) {
		this.user = user;
		this.vehicle = vehicle;
		this.parkingSpot = parkingSpot;
		startTime = LocalDateTime.now();
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"objectId=" + objectId +
				", user=" + user +
				", vehicle=" + vehicle +
				", parkingSpot=" + parkingSpot +
				", startTime=" + startTime +
				'}';
	}
}

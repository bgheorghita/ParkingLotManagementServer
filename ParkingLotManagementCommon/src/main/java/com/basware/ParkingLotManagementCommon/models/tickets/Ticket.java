package com.basware.ParkingLotManagementCommon.models.tickets;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.users.User;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

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

	private Price price;

	private int parkingDurationTimeInMinutes;


	public Ticket(){}

	public Ticket(User user, Vehicle vehicle, ParkingSpot parkingSpot, Price price, int parkingDurationTimeInMinutes) {
		this.user = user;
		this.vehicle = vehicle;
		this.parkingSpot = parkingSpot;
		this.price = price;
		this.parkingDurationTimeInMinutes = parkingDurationTimeInMinutes;
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

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public int getParkingDurationTimeInMinutes() {
		return parkingDurationTimeInMinutes;
	}

	public void setParkingDurationTimeInMinutes(int parkingDurationTimeInMinutes) {
		this.parkingDurationTimeInMinutes = parkingDurationTimeInMinutes;
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"objectId=" + objectId +
				", user=" + user +
				", vehicle=" + vehicle +
				", parkingSpot=" + parkingSpot +
				", price=" + price +
				", parkingDurationTimeInMinutes=" + parkingDurationTimeInMinutes +
				'}';
	}
}

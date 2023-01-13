package com.basware.ParkingLotManagementCommon.models.parking.spots;


import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import dev.morphia.annotations.*;
import org.bson.types.ObjectId;


@Entity("parkingSpots")
public class ParkingSpot {
	public static final String PARKING_SPOT_TYPE_FIELD = "parkingSpotType";
	public static final String HAS_ELECTRIC_CHARGER_FIELD = "hasElectricCharger";

	@Id
	private ObjectId objectId;
	@Reference(ignoreMissing = true, idOnly = true)
	private Vehicle vehicle;
	@Property(ParkingSpot.PARKING_SPOT_TYPE_FIELD)
	private ParkingSpotType parkingSpotType;
	@Property(ParkingSpot.HAS_ELECTRIC_CHARGER_FIELD)
	private boolean hasElectricCharger;
	private boolean isEmpty;
	protected ParkingSpot(){}
	public ParkingSpot(ParkingSpotType parkingSpotType, boolean hasElectricCharger) {
		this.parkingSpotType = parkingSpotType;
		this.hasElectricCharger = hasElectricCharger;
		isEmpty = true;
	}

	public ParkingSpotType getParkingSpotType(){
		return parkingSpotType;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		isEmpty = false;
	}

	public void removeVehicle(){
		vehicle = null;
		isEmpty = true;
	}

	public boolean isEmpty(){return vehicle == null;}

	public boolean hasElectricCharger(){return hasElectricCharger;}

	@Override
	public String toString() {
		return "ParkingSpot{" +
				"objectId=" + objectId +
				", vehicle=" + vehicle +
				", parkingSpotType=" + parkingSpotType +
				", hasElectricCharger=" + hasElectricCharger +
				", isEmpty=" + isEmpty +
				'}';
	}
}

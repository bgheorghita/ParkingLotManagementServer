package com.basware.ParkingLotManagementCommon.models.parking.spots;


import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.morphia.annotations.*;
import org.bson.types.ObjectId;


@Entity("parkingSpots")
@Indexes({
		@Index(options = @IndexOptions(name = "unique_spot_number", unique = true),
				fields = @Field(value = ParkingSpot.SPOT_NUMBER)),
})
public class ParkingSpot {
	public static final String PARKING_SPOT_TYPE_FIELD = "parkingSpotType";
	public static final String HAS_ELECTRIC_CHARGER_FIELD = "hasElectricCharger";
	public static final String VEHICLE_FIELD = "vehicle";
	public static final String IS_FREE_FIELD = "isFree";
	public static final String SPOT_NUMBER = "spotNumber";

	@Version
	private Long version;

	@Id
	private ObjectId objectId;

	@Property(ParkingSpot.SPOT_NUMBER)
	private Long spotNumber;

	@Reference(value = VEHICLE_FIELD, ignoreMissing = true, idOnly = true)
	private Vehicle vehicle;

	@Property(ParkingSpot.PARKING_SPOT_TYPE_FIELD)
	private ParkingSpotType parkingSpotType;

	@Property(ParkingSpot.HAS_ELECTRIC_CHARGER_FIELD)
	@JsonProperty(ParkingSpot.HAS_ELECTRIC_CHARGER_FIELD)
	private boolean hasElectricCharger;

	@Property(ParkingSpot.IS_FREE_FIELD)
	private boolean isFree;

	protected ParkingSpot(){}

	public ParkingSpot(ParkingSpotType parkingSpotType, Long spotNumber, boolean hasElectricCharger) {
		this.parkingSpotType = parkingSpotType;
		this.spotNumber = spotNumber;
		this.hasElectricCharger = hasElectricCharger;
		isFree = true;
	}

	public ParkingSpotType getParkingSpotType(){
		return parkingSpotType;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public ObjectId getObjectId(){
		return objectId;
	}

	public Long getSpotNumber() {
		return spotNumber;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		isFree = false;
	}

	public void removeVehicle(){
		vehicle = null;
		isFree = true;
	}

	public boolean isFree(){return vehicle == null;}

	public boolean hasElectricCharger(){return hasElectricCharger;}

	@Override
	public String toString() {
		return "ParkingSpot{" +
				"objectId=" + objectId +
				", vehicle=" + vehicle +
				", parkingSpotType=" + parkingSpotType +
				", hasElectricCharger=" + hasElectricCharger +
				", isFree=" + isFree +
				", spotId='" + spotNumber + '\'' +
				'}';
	}
}

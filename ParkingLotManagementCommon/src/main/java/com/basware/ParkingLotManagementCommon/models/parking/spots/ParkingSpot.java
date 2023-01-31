package com.basware.ParkingLotManagementCommon.models.parking.spots;


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
	public static final String VEHICLE_PLATE_NUMBER = "vehiclePlateNumber";
	public static final String IS_FREE_FIELD = "isFree";
	public static final String SPOT_NUMBER = "spotNumber";

	@Version
	private Long version;

	@Id
	private ObjectId objectId;

	@Property(ParkingSpot.SPOT_NUMBER)
	private Long spotNumber;

	@Property(ParkingSpot.VEHICLE_PLATE_NUMBER)
	private String vehiclePlateNumber;

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

	public String getVehiclePlateNumber() {
		return vehiclePlateNumber;
	}

	public ObjectId getObjectId(){
		return objectId;
	}

	public Long getSpotNumber() {
		return spotNumber;
	}

	public void setVehiclePlateNumber(String vehiclePlateNumber) {
		this.vehiclePlateNumber = vehiclePlateNumber;
		isFree = false;
	}

	public void removeVehiclePlateNumber(){
		vehiclePlateNumber = null;
		isFree = true;
	}

	public boolean isFree(){return vehiclePlateNumber != null;}

	public boolean hasElectricCharger(){return hasElectricCharger;}

	@Override
	public String toString() {
		return "ParkingSpot{" +
				"version=" + version +
				", objectId=" + objectId +
				", spotNumber=" + spotNumber +
				", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
				", parkingSpotType=" + parkingSpotType +
				", hasElectricCharger=" + hasElectricCharger +
				", isFree=" + isFree +
				'}';
	}
}

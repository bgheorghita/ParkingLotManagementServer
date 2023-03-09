package com.basware.ParkingLotManagementCommon.models.vehicles;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

@Entity(Vehicle.VEHICLE_COLLECTION_NAME)
@Indexes({
		@Index(options = @IndexOptions(name = "unique_plate_number", unique = true),
				fields = @Field(value = Vehicle.VEHICLE_PLATE_NUMBER_FIELD)),
})
public class Vehicle {
	public static final String VEHICLE_COLLECTION_NAME = "vehicles";
	public static final String VEHICLE_TYPE_FIELD = "vehicleType";
	public static final String VEHICLE_PLATE_NUMBER_FIELD = "plateNumber";
	@Id
	private ObjectId objectId;

	@Property(VEHICLE_TYPE_FIELD)
	private VehicleType vehicleType;

	@Property(VEHICLE_PLATE_NUMBER_FIELD)
	private String plateNumber;
	private boolean isElectric;

	public Vehicle(){}

	public Vehicle(VehicleType vehicleType, String plateNumber, boolean isElectric) {
		this.vehicleType = vehicleType;
		this.plateNumber = plateNumber;
		this.isElectric = isElectric;
	}

	public ObjectId getObjectId(){
		return objectId;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public String getPlateNumber(){
		return plateNumber;
	}
	public boolean getIsElectric(){
		return isElectric;
	}

	@Override
	public String toString() {
		return "Vehicle{" +
				"objectId=" + objectId +
				", vehicleType=" + vehicleType +
				", plateNumber='" + plateNumber + '\'' +
				", isElectric=" + isElectric +
				'}';
	}
}
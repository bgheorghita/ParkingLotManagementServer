package com.basware.ParkingLotManagementCommon.models.vehicles;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.util.Objects;

@Entity("vehicles")
@Indexes({
		@Index(options = @IndexOptions(name = "unique_vehicle_type", unique = true),
				fields = @Field(value = "vehicleType"))
})
public abstract class Vehicle {
	@Id
	private ObjectId objectId;
	private VehicleType vehicleType;
	private String plateNumber;
	private boolean isElectric;

	protected Vehicle(){}
	protected Vehicle(VehicleType vehicleType, String plateNumber, boolean isElectric) {
		this.vehicleType = vehicleType;
		this.plateNumber = plateNumber;
		this.isElectric = isElectric;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public String getPlateNumber(){
		return plateNumber;
	}
	public boolean isElectric(){
		return isElectric;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vehicle vehicle = (Vehicle) o;
		return isElectric == vehicle.isElectric && vehicleType == vehicle.vehicleType && plateNumber.equals(vehicle.plateNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vehicleType, plateNumber, isElectric);
	}

	@Override
	public String toString() {
		return "Vehicle{" +
				"vehicleType=" + vehicleType +
				", plateNumber='" + plateNumber + '\'' +
				", isElectric=" + isElectric +
				'}';
	}
}

package com.basware.ParkingLotManagementCommon.models.parking.spots;


import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import dev.morphia.annotations.*;
import org.bson.types.ObjectId;


@Entity("parkingSpots")
@Indexes({
		@Index(options = @IndexOptions(name = "unique_parking_spot_type", unique = true),
				fields = @Field(value = "parkingSpotType"))
})
public abstract class ParkingSpot {

	@Id
	private ObjectId objectId;
	private Vehicle vehicle;
	private ParkingSpotType parkingSpotType;
	private boolean hasElectricCharger;

	protected ParkingSpot(){}
	protected ParkingSpot(ParkingSpotType parkingSpotType, boolean hasElectricCharger) {
		this.parkingSpotType = parkingSpotType;
		this.hasElectricCharger = hasElectricCharger;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public boolean isEmpty(){return vehicle == null;}

	public boolean hasElectricCharger(){return hasElectricCharger;}
}

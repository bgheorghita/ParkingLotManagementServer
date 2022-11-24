package com.basware.ParkingLotManagementServer.models.vehicles;

public class Motorcycle extends Vehicle{

	public Motorcycle(String plateNumber, boolean isElectric) {
		super(VehicleType.MOTORCYCLE, plateNumber, isElectric);
	}

}

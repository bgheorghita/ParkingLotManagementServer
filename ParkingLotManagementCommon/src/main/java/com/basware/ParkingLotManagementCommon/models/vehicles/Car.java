package com.basware.ParkingLotManagementCommon.models.vehicles;

public class Car extends Vehicle{

	public Car(){}
	public Car(String plateNumber, boolean isElectric) {
		super(VehicleType.CAR, plateNumber, isElectric);
	}

}

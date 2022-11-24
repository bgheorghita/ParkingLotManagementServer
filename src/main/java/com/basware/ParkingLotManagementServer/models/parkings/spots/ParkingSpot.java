package com.basware.ParkingLotManagementServer.models.parkings.spots;


import com.basware.ParkingLotManagementServer.models.vehicles.Vehicle;


public abstract class ParkingSpot {
	private Vehicle vehicle;
	private final ParkingSpotType parkingSpotType;
	private final boolean hasElectricCharger;

	protected ParkingSpot(ParkingSpotType parkingSpotType, boolean hasElectricCharger) {
		this.parkingSpotType = parkingSpotType;
		this.hasElectricCharger = hasElectricCharger;
	}

	public boolean isEmpty(){return vehicle == null;}

	public boolean hasElectricCharger(){return hasElectricCharger;}
}

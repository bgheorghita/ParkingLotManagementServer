package com.basware.ParkingLotManagementCommon.models.parking.spots;

public class MediumParkingSpot extends ParkingSpot{
	public MediumParkingSpot(){}
	public MediumParkingSpot(boolean hasElectricCharger) {
		super(ParkingSpotType.MEDIUM, hasElectricCharger);
	}
}

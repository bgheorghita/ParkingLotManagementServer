package com.basware.ParkingLotManagementCommon.models.parkings.spots;

public class SmallParkingSpot extends ParkingSpot{
	public SmallParkingSpot(boolean hasElectricCharger) {
		super(ParkingSpotType.SMALL, hasElectricCharger);
	}
}

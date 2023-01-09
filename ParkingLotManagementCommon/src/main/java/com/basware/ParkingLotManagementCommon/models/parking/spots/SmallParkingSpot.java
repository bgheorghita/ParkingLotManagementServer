package com.basware.ParkingLotManagementCommon.models.parking.spots;

public class SmallParkingSpot extends ParkingSpot{
	public SmallParkingSpot(){}
	public SmallParkingSpot(boolean hasElectricCharger) {
		super(ParkingSpotType.SMALL, hasElectricCharger);
	}
}

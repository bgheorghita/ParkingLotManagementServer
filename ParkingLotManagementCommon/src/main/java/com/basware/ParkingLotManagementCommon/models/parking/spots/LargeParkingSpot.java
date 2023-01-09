package com.basware.ParkingLotManagementCommon.models.parking.spots;

public class LargeParkingSpot extends ParkingSpot{
	public LargeParkingSpot(){}
	public LargeParkingSpot(boolean hasElectricCharger) {
		super(ParkingSpotType.LARGE, hasElectricCharger);
	}
}

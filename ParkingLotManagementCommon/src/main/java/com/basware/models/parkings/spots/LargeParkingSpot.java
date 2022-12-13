package com.basware.models.parkings.spots;

public class LargeParkingSpot extends ParkingSpot{
	public LargeParkingSpot(boolean hasElectricCharger) {
		super(ParkingSpotType.LARGE, hasElectricCharger);
	}
}

package com.basware.ParkingLotManagementWeb.services.parking.strategies;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;

import java.util.Optional;

public interface ParkingStrategyByUserType {
    Optional<ParkingSpot> findParkingSpot(Vehicle vehicle);
    UserType getUserTypeForThisParkingStrategy();
}

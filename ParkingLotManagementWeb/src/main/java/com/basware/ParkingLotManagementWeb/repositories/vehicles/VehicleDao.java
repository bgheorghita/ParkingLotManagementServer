package com.basware.ParkingLotManagementWeb.repositories.vehicles;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;

import java.util.Optional;

public interface VehicleDao extends CrudRepository<Vehicle> {
    Optional<Vehicle> findVehicleByPlateNumber(String plateNumber);
}

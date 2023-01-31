package com.basware.ParkingLotManagementWeb.repositories.vehicles;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;

import java.util.List;

public interface VehicleDao extends CrudRepository<Vehicle> {
    List<Vehicle> findAllByPlateNumber(String plateNumber);
}

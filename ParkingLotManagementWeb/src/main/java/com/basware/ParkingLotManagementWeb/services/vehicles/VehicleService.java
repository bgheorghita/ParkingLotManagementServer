package com.basware.ParkingLotManagementWeb.services.vehicles;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.services.CrudService;

import java.util.List;


public interface VehicleService extends CrudService<Vehicle> {
    Vehicle findFirstByPlateNumber(String plateNumber) throws ResourceNotFoundException;
    boolean deleteByPlateNumber(String plateNumber);
    List<Vehicle> findParkedVehicles();
}

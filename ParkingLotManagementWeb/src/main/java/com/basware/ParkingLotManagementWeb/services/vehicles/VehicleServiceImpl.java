package com.basware.ParkingLotManagementWeb.services.vehicles;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.vehicles.VehicleDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl extends CrudServiceImpl<Vehicle> implements VehicleService{

    public VehicleServiceImpl(VehicleDao vehicleDao) {
        super(vehicleDao);
    }

    @Override
    public Vehicle findByPlateNumber(String plateNumber) throws ResourceNotFoundException {
        return ((VehicleDao)crudRepository).findVehicleByPlateNumber(plateNumber).orElseThrow(ResourceNotFoundException::new);
    }
}

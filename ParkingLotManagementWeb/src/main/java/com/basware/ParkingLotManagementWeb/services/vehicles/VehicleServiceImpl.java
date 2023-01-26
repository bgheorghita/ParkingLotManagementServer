package com.basware.ParkingLotManagementWeb.services.vehicles;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.vehicles.VehicleDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class VehicleServiceImpl extends CrudServiceImpl<Vehicle> implements VehicleService{
    private final VehicleDao vehicleDao;

    public VehicleServiceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Vehicle findByPlateNumber(String plateNumber) throws ResourceNotFoundException {
        return vehicleDao.findVehicleByPlateNumber(plateNumber).orElseThrow(ResourceNotFoundException::new);
    }
}

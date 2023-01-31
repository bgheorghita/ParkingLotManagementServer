package com.basware.ParkingLotManagementWeb.services.vehicles;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.vehicles.VehicleDao;
import com.basware.ParkingLotManagementWeb.services.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VehicleServiceImpl extends CrudServiceImpl<Vehicle> implements VehicleService{
    private final VehicleDao vehicleDao;

    public VehicleServiceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Vehicle findFirstByPlateNumber(String plateNumber) throws ResourceNotFoundException {
        List<Vehicle> vehicles = vehicleDao.findAllByPlateNumber(plateNumber);
        if(vehicles.size() > 0){
            return vehicles.get(0);
        }
        throw new ResourceNotFoundException(String.format("Vehicle by plate number \"%s\" could not be found", plateNumber));
    }
}
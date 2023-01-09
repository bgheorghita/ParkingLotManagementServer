package com.basware.ParkingLotManagementWeb.services.vehicles;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementWeb.repositories.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService{

    private final CrudRepository<Vehicle> vehicleCrudRepository;

    public VehicleServiceImpl(CrudRepository<Vehicle> vehicleCrudRepository){
        this.vehicleCrudRepository = vehicleCrudRepository;
    }
    @Override
    public boolean save(Vehicle vehicle) {
        return vehicleCrudRepository.save(vehicle);
    }
}

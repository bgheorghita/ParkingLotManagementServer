package com.basware.ParkingLotManagementServer.services.taxes.prices.impl;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.repositories.taxes.VehicleTypePriceDao;
import com.basware.ParkingLotManagementServer.services.taxes.prices.VehiclePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiclePriceServiceImpl implements VehiclePriceService {
    @Autowired
    private VehicleTypePriceDao vehicleTypePriceDao;

    public Price getPrice(VehicleType vehicleType) throws ResourceNotFoundException {
        Optional<Price> vehiclePriceOptional = vehicleTypePriceDao.findByVehicleType(vehicleType);
        if(vehiclePriceOptional.isPresent()){
            return vehiclePriceOptional.get();
        } else {
            String msg = "Resource vehicle type \"" + vehicleType + "\" has not been found";
            throw new ResourceNotFoundException(msg);
        }
    }
}


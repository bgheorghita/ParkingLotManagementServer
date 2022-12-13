package com.basware.ParkingLotManagementWeb.services.taxes.prices.impl;

import com.basware.ParkingLotManagementCommon.models.taxes.Price;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.repositories.taxes.VehicleTypePriceDao;
import com.basware.ParkingLotManagementWeb.services.taxes.prices.VehiclePriceService;
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


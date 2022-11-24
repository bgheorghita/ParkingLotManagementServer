package com.basware.ParkingLotManagementServer.services.taxes.prices;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.taxes.VehiclePrice;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.repositories.taxes.VehiclePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiclePriceServiceImpl implements VehiclePriceService {
    @Autowired
    private VehiclePriceRepository vehiclePriceRepository;

    public Price getPrice(VehicleType vehicleType) throws ResourceNotFoundException {
        Optional<VehiclePrice> vehiclePriceOptional = vehiclePriceRepository.findByVehicleType(vehicleType);
        if(vehiclePriceOptional.isPresent()){
            return vehiclePriceOptional.get().getPrice();
        } else {
            String msg = "Resource vehicle type \"" + vehicleType + "\" has not been found";
            throw new ResourceNotFoundException(msg);
        }
    }
}


package com.basware.ParkingLotManagementWeb.api.v1.mappers;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleOutputMapper {

    @Autowired
    private ParkingSpotService parkingSpotService;

    public VehicleDto fromVehicleToVehicleDto(Vehicle vehicle) {
        VehicleType vehicleType = vehicle.getVehicleType();
        String plateNumber = vehicle.getPlateNumber();
        boolean isElectric = vehicle.getIsElectric();
        boolean isParked = true;

        try{ parkingSpotService.findFirstByVehiclePlateNumber(vehicle.getPlateNumber()); }
        catch (ResourceNotFoundException e){ isParked = false; }

        return new VehicleDto(vehicleType, plateNumber, isElectric, isParked);
    }
}
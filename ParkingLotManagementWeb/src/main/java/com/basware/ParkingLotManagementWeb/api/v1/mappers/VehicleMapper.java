package com.basware.ParkingLotManagementWeb.api.v1.mappers;

import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehicleDto;

public class VehicleMapper {
    public static VehicleDto fromVehicleToVehicleDto(Vehicle vehicle){
        VehicleType vehicleType = vehicle.getVehicleType();
        String plateNumber = vehicle.getPlateNumber();
        boolean isElectric = vehicle.getIsElectric();
        boolean isParked = vehicle.getIsParked();

        return new VehicleDto(vehicleType, plateNumber, isElectric, isParked);
    }
}

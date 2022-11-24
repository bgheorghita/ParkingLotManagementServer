package com.basware.ParkingLotManagementServer.api.v1.mappers.taxes;

import com.basware.ParkingLotManagementServer.api.v1.models.VehiclePriceDto;
import com.basware.ParkingLotManagementServer.models.taxes.VehiclePrice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehiclePriceMapper {
    VehiclePriceMapper INSTANCE = Mappers.getMapper(VehiclePriceMapper.class);

    VehiclePriceDto toDto(VehiclePrice vehiclePrice);
    VehiclePrice fromDto(VehiclePriceDto vehiclePriceDto);
}

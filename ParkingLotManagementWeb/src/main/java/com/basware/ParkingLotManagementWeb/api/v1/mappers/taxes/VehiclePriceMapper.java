package com.basware.ParkingLotManagementWeb.api.v1.mappers.taxes;

import com.basware.ParkingLotManagementCommon.models.taxes.VehiclePrice;
import com.basware.ParkingLotManagementWeb.api.v1.models.VehiclePriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehiclePriceMapper {
    VehiclePriceMapper INSTANCE = Mappers.getMapper(VehiclePriceMapper.class);

    VehiclePriceDto toDto(VehiclePrice vehiclePrice);
    VehiclePrice fromDto(VehiclePriceDto vehiclePriceDto);
}

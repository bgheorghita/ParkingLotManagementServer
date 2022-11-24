package com.basware.ParkingLotManagementServer.api.v1.mappers.taxes;

import com.basware.ParkingLotManagementServer.api.v1.models.ParkingSpotPriceDto;
import com.basware.ParkingLotManagementServer.models.taxes.ParkingSpotPrice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingSpotPriceMapper {
    ParkingSpotPriceMapper INSTANCE = Mappers.getMapper(ParkingSpotPriceMapper.class);

    ParkingSpotPriceDto toDto(ParkingSpotPrice parkingSpotPrice);
    ParkingSpotPrice fromDto(ParkingSpotPriceDto parkingSpotPriceDto);
}

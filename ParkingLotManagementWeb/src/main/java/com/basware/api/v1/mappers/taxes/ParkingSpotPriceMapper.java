package com.basware.api.v1.mappers.taxes;

import com.basware.models.taxes.ParkingSpotPrice;
import com.basware.api.v1.models.ParkingSpotPriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkingSpotPriceMapper {
    ParkingSpotPriceMapper INSTANCE = Mappers.getMapper(ParkingSpotPriceMapper.class);

    ParkingSpotPriceDto toDto(ParkingSpotPrice parkingSpotPrice);
    ParkingSpotPrice fromDto(ParkingSpotPriceDto parkingSpotPriceDto);
}

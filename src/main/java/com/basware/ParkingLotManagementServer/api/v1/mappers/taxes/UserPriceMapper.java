package com.basware.ParkingLotManagementServer.api.v1.mappers.taxes;

import com.basware.ParkingLotManagementServer.api.v1.models.UserDiscountDto;
import com.basware.ParkingLotManagementServer.api.v1.models.UserPriceDto;
import com.basware.ParkingLotManagementServer.models.taxes.UserPrice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserPriceMapper {
    UserPriceMapper INSTANCE = Mappers.getMapper(UserPriceMapper.class);

    UserPriceDto toDto(UserPrice userPrice);
    UserPrice fromDto(UserDiscountDto userDiscountDto);
}

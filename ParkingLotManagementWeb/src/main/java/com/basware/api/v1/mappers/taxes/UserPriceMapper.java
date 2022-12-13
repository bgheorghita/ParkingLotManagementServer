package com.basware.api.v1.mappers.taxes;

import com.basware.api.v1.models.UserPriceDto;
import com.basware.models.taxes.UserPrice;
import com.basware.api.v1.models.UserDiscountDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserPriceMapper {
    UserPriceMapper INSTANCE = Mappers.getMapper(UserPriceMapper.class);

    UserPriceDto toDto(UserPrice userPrice);
    UserPrice fromDto(UserDiscountDto userDiscountDto);
}

package com.basware.api.v1.mappers.taxes;

import com.basware.models.taxes.discounts.UserDiscount;
import com.basware.api.v1.models.UserDiscountDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDiscountMapper {
    UserDiscountMapper INSTANCE = Mappers.getMapper(UserDiscountMapper.class);

    UserDiscountDto toDto(UserDiscount userDiscount);
    UserDiscount fromDto(UserDiscountDto userDiscountDto);
}

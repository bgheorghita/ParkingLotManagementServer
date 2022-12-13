package com.basware.api.v1.models;


import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;

public class UserDiscountDto extends UserPriceDto{
    public UserDiscountDto(UserType userType, Price price) {
        super(userType, price);
    }
}

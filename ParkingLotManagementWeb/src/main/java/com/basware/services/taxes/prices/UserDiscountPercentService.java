package com.basware.services.taxes.prices;


import com.basware.models.users.UserType;

public interface UserDiscountPercentService {
    Double getDiscountPercent(UserType userType);
}

package com.basware.services.taxes.prices;

import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import com.basware.exceptions.ResourceNotFoundException;

public interface UserPriceService {
    Price getPrice(UserType userType) throws ResourceNotFoundException;
}

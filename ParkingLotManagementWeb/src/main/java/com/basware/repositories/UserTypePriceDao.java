package com.basware.repositories;


import com.basware.models.taxes.Price;
import com.basware.models.taxes.UserPrice;
import com.basware.models.users.UserType;

import java.util.Optional;

public interface UserTypePriceDao {
    Optional<Price> findByUserType(UserType userType);
    void save(UserPrice userPrice);
    void deleteAll();
}

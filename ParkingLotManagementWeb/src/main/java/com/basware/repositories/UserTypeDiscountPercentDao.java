package com.basware.repositories;

import com.basware.models.taxes.discounts.UserDiscount;
import com.basware.models.users.UserType;

import java.util.Optional;

public interface UserTypeDiscountPercentDao {
    Optional<Double> findByUserType(UserType userType);
    void save(UserDiscount userPrice);
    void deleteAll();
}

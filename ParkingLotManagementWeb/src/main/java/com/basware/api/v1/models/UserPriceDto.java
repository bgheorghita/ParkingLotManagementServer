package com.basware.api.v1.models;

import com.basware.models.taxes.Price;
import com.basware.models.users.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPriceDto {
    private UserType userType;
    private Price price;
}

package com.basware.api.v1.models;

import com.basware.models.parkings.spots.ParkingSpotType;
import com.basware.models.taxes.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingSpotPriceDto {
    private ParkingSpotType parkingSpotType;
    private Price price;
}

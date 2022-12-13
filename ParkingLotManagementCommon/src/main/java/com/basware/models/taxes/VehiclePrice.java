package com.basware.models.taxes;

import com.basware.models.vehicles.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehiclePrice {
    private VehicleType vehicleType;
    private Price price;
}

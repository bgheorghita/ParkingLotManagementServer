package com.basware.ParkingLotManagementServer.models.taxes;

import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParkingSpotPrice {
    private ParkingSpotType parkingSpotType;
    private Price price;
}


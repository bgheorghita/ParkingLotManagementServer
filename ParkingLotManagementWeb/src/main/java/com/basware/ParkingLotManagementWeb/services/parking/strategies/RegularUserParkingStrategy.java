package com.basware.ParkingLotManagementWeb.services.parking.strategies;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RegularUserParkingStrategy implements ParkingStrategyByUserType {
    private final ParkingSpotService parkingSpotService;
    private final Map<VehicleType, ParkingSpotType> map = Map.ofEntries(
            Map.entry(VehicleType.MOTORCYCLE, ParkingSpotType.SMALL),
            Map.entry(VehicleType.CAR, ParkingSpotType.MEDIUM),
            Map.entry(VehicleType.TRUCK, ParkingSpotType.LARGE)
    );

    public RegularUserParkingStrategy(ParkingSpotService parkingSpotService){
        this.parkingSpotService = parkingSpotService;
    }

    @Override
    public Optional<ParkingSpot> findParkingSpot(Vehicle vehicle) {
        List<ParkingSpot> availableSpots = parkingSpotService.findAllFreeByParkingSpotType(map.get(vehicle.getVehicleType()));

        Optional<ParkingSpot> parkingSpot = getParkingSpotFromList(availableSpots, vehicle.isElectric());
        if(parkingSpot.isEmpty()){
            parkingSpot = getParkingSpotFromList(availableSpots, !vehicle.isElectric());
        }

        return parkingSpot;
    }

    Optional<ParkingSpot> getParkingSpotFromList(List<ParkingSpot> parkingSpotList, boolean electricCharger){
        return parkingSpotList
                .stream()
                .filter(parkingSpot -> parkingSpot.hasElectricCharger() == electricCharger)
                .findFirst();
    }

    @Override
    public UserType getUserTypeForThisParkingStrategy() {
        return UserType.REGULAR;
    }
}

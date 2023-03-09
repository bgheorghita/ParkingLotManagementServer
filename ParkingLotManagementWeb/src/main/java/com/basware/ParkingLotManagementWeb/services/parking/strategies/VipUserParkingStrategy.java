package com.basware.ParkingLotManagementWeb.services.parking.strategies;

import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpot;
import com.basware.ParkingLotManagementCommon.models.parking.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.Vehicle;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VipUserParkingStrategy implements ParkingStrategyByUserType {
    private final ParkingSpotService parkingSpotService;

    private final Map<VehicleType, Set<ParkingSpotType>> map = Map.ofEntries(
            Map.entry(VehicleType.MOTORCYCLE, Set.of(ParkingSpotType.SMALL, ParkingSpotType.MEDIUM, ParkingSpotType.LARGE)),
            Map.entry(VehicleType.CAR, Set.of(ParkingSpotType.MEDIUM, ParkingSpotType.LARGE)),
            Map.entry(VehicleType.TRUCK, Set.of(ParkingSpotType.LARGE))
    );

    public VipUserParkingStrategy(ParkingSpotService parkingSpotService){
        this.parkingSpotService = parkingSpotService;
    }

    @Override
    public Optional<ParkingSpot> findParkingSpot(Vehicle vehicle) {
        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllFree();
        List<ParkingSpotType> fittingParkingSpotTypes = map.get(vehicle.getVehicleType())
                .stream()
                .sorted(Enum::compareTo)
                .collect(Collectors.toUnmodifiableList());

        return getParkingSpotFromList(availableParkingSpots, fittingParkingSpotTypes, vehicle.getIsElectric())
                .or(() -> getParkingSpotFromList(availableParkingSpots, fittingParkingSpotTypes, !vehicle.getIsElectric()));
    }

    private Optional<ParkingSpot> getParkingSpotFromList(List<ParkingSpot> availableParkingSpots, List<ParkingSpotType> fittingParkingSpotTypes, boolean hasElectricCharger) {
        for (ParkingSpotType parkingSpotType : fittingParkingSpotTypes) {
            for(ParkingSpot parkingSpot : availableParkingSpots){
                if(parkingSpot.getParkingSpotType().equals(parkingSpotType) && (parkingSpot.hasElectricCharger() == hasElectricCharger)){
                    return Optional.of(parkingSpot);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public UserType getUserTypeForThisParkingStrategy() {
        return UserType.VIP;
    }
}
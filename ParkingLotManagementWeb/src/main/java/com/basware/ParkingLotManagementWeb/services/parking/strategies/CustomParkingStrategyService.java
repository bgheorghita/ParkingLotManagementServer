package com.basware.ParkingLotManagementWeb.services.parking.strategies;

import com.basware.ParkingLotManagementCommon.models.users.UserType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomParkingStrategyService {
    private final Map<UserType, ParkingStrategyByUserType> parkingStrategyByUserType;

    public CustomParkingStrategyService(List<ParkingStrategyByUserType> parkingStrategies){
        parkingStrategyByUserType = parkingStrategies.stream()
                .collect(Collectors.toMap(ParkingStrategyByUserType::getUserTypeForThisParkingStrategy, Function.identity()));
    }

    public ParkingStrategyByUserType getParkingStrategyByUserType(UserType userType){
        return parkingStrategyByUserType.get(userType);
    }
}

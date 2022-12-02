package com.basware.ParkingLotManagementServer.services.taxes.prices.impl;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.repositories.taxes.ParkingSpotTypePriceDao;
import com.basware.ParkingLotManagementServer.services.taxes.prices.ParkingSpotPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingSpotPriceServiceImpl implements ParkingSpotPriceService {
    @Autowired
    private ParkingSpotTypePriceDao parkingSpotTypePriceDao;

    @Override
    public Price getPrice(ParkingSpotType parkingSpotType) throws ResourceNotFoundException {
        Optional<Price> parkingSpotPriceOptional = parkingSpotTypePriceDao.findByParkingSpotType(parkingSpotType);
        if(parkingSpotPriceOptional.isPresent()){
            return parkingSpotPriceOptional.get();
        } else {
            String exceptionMsg = "Parking spot type \"" + parkingSpotType+ "\" has not been found";
            throw new ResourceNotFoundException(exceptionMsg);
        }
    }
}

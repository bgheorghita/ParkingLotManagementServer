package com.basware.ParkingLotManagementServer.services.taxes.prices;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.ParkingSpotPrice;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.repositories.taxes.ParkingSpotPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingSpotPriceServiceImpl implements ParkingSpotPriceService {
    @Autowired
    private ParkingSpotPriceRepository parkingSpotPriceRepository;

    @Override
    public Price getPrice(ParkingSpotType parkingSpotType) throws ResourceNotFoundException {
        Optional<ParkingSpotPrice> parkingSpotPriceOptional = parkingSpotPriceRepository.findByParkingSpotType(parkingSpotType);
        if(parkingSpotPriceOptional.isPresent()){
            return parkingSpotPriceOptional.get().getPrice();
        } else {
            String exceptionMsg = "Parking spot type \"" + parkingSpotType+ "\" has not been found";
            throw new ResourceNotFoundException(exceptionMsg);
        }
    }
}

package com.basware.ParkingLotManagementServer.services.taxes.prices;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.taxes.UserPrice;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.repositories.taxes.UserPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPriceServiceImpl implements UserPriceService {
    @Autowired
    private UserPriceRepository userPriceRepository;

    @Override
    public Price getPrice(UserType userType) throws ResourceNotFoundException {
        Optional<UserPrice> userPriceOptional = userPriceRepository.findByUserType(userType);
        if(userPriceOptional.isPresent()) {
            return userPriceOptional.get().getPrice();
        } else {
            String msg = "Resource user type \"" + userType + "\" has not been found";
            throw new ResourceNotFoundException(msg);
        }
    }
}

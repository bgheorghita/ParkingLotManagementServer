package com.basware.ParkingLotManagementWeb.bootstraps;

import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.*;
import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.repositories.taxes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ParkingSpotTypePriceDao parkingSpotTypePriceDao;

    @Autowired
    private UserTypePriceDao userTypePriceDao;

    @Autowired
    private VehicleTypePriceDao vehicleTypePriceDao;

    @Autowired
    private UserTypeDiscountPercentDao userTypeDiscountPercentDao;

//    @Autowired
//    private TypePriceDao typePriceDao;

    @Override
    public void run(String... args) {
        parkingSpotTypePriceDao.deleteAll();
        userTypePriceDao.deleteAll();
        vehicleTypePriceDao.deleteAll();
        userTypeDiscountPercentDao.deleteAll();
        loadPricesForParkingSpots();
        loadPricesForUsers();
        loadPricesForVehicles();
        loadUserDiscounts();

        //typePriceDao.save(new TypePrice(ParkingSpotType.LARGE, new Price(100, Currency.EUR)));
    }

    private void loadUserDiscounts() {
        userTypeDiscountPercentDao.save(new UserDiscount(UserType.REGULAR, 0.25));
        userTypeDiscountPercentDao.save(new UserDiscount(UserType.VIP, 0.50));
    }

    private void loadPricesForVehicles() {
        vehicleTypePriceDao.save(new VehiclePrice(VehicleType.MOTORCYCLE, new Price(0.5, Currency.EUR)));
        vehicleTypePriceDao.save(new VehiclePrice(VehicleType.CAR, new Price(0.15, Currency.EUR)));
        vehicleTypePriceDao.save(new VehiclePrice(VehicleType.TRUCK, new Price(0.20, Currency.EUR)));
    }

    private void loadPricesForParkingSpots() {
        parkingSpotTypePriceDao.save(new ParkingSpotPrice(ParkingSpotType.SMALL, new Price(0.1, Currency.EUR)));
        parkingSpotTypePriceDao.save(new ParkingSpotPrice(ParkingSpotType.MEDIUM, new Price(0.2, Currency.EUR)));
        parkingSpotTypePriceDao.save(new ParkingSpotPrice(ParkingSpotType.LARGE, new Price(0.3, Currency.EUR)));
    }

    private void loadPricesForUsers() {
        userTypePriceDao.save(new UserPrice(UserType.VIP, new Price(0.5, Currency.EUR)));
        userTypePriceDao.save(new UserPrice(UserType.REGULAR, new Price(1.0, Currency.EUR)));
    }
}

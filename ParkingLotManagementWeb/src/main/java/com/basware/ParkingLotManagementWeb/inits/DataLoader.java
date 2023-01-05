package com.basware.ParkingLotManagementWeb.inits;

import com.basware.ParkingLotManagementCommon.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementCommon.models.taxes.*;
import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementWeb.repositories.taxes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UserTypeDiscountPercentDao userTypeDiscountPercentDao;

    @Autowired
    private TypePriceDao typePriceDao;

    @Override
    public void run(String... args) {
        typePriceDao.deleteAll();
        userTypeDiscountPercentDao.deleteAll();
        loadPricesForParkingSpots();
        loadPricesForUsers();
        loadPricesForVehicles();
        loadUserDiscounts();
    }

    private void loadUserDiscounts() {
        userTypeDiscountPercentDao.save(new UserDiscount(UserType.REGULAR, 0.25));
        userTypeDiscountPercentDao.save(new UserDiscount(UserType.VIP, 0.50));
    }

    private void loadPricesForVehicles() {
        typePriceDao.save(new TypePrice(new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.MOTORCYCLE.name()), new Price(0.5, Currency.EUR)));
        typePriceDao.save(new TypePrice(new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.CAR.name()), new Price(0.15, Currency.EUR)));
        typePriceDao.save(new TypePrice(new TypeInfo(TypeInfo.VEHICLE_IDENTIFIER, VehicleType.TRUCK.name()), new Price(0.20, Currency.EUR)));
    }

    private void loadPricesForParkingSpots() {
        typePriceDao.save(new TypePrice(new TypeInfo(TypeInfo.PARKING_SPOT_IDENTIFIER, ParkingSpotType.SMALL.name()), new Price(0.1, Currency.EUR)));
        typePriceDao.save(new TypePrice(new TypeInfo(TypeInfo.PARKING_SPOT_IDENTIFIER, ParkingSpotType.MEDIUM.name()), new Price(0.2, Currency.EUR)));
        typePriceDao.save(new TypePrice(new TypeInfo(TypeInfo.PARKING_SPOT_IDENTIFIER, ParkingSpotType.LARGE.name()), new Price(0.3, Currency.EUR)));
    }

    private void loadPricesForUsers() {
        typePriceDao.save(new TypePrice(new TypeInfo(TypeInfo.USER_IDENTIFIER, UserType.REGULAR.name()), new Price(0.5, Currency.EUR)));
        typePriceDao.save(new TypePrice(new TypeInfo(TypeInfo.USER_IDENTIFIER, UserType.VIP.name()), new Price(1.0, Currency.EUR)));
    }
}

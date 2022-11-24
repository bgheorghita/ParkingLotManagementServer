package com.basware.ParkingLotManagementServer.bootstraps;

import com.basware.ParkingLotManagementServer.models.taxes.Currency;
import com.basware.ParkingLotManagementServer.models.taxes.Price;
import com.basware.ParkingLotManagementServer.models.taxes.*;
import com.basware.ParkingLotManagementServer.models.parkings.spots.ParkingSpotType;
import com.basware.ParkingLotManagementServer.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementServer.models.users.UserType;
import com.basware.ParkingLotManagementServer.models.vehicles.VehicleType;
import com.basware.ParkingLotManagementServer.repositories.taxes.ParkingSpotPriceRepository;
import com.basware.ParkingLotManagementServer.repositories.taxes.UserPriceRepository;
import com.basware.ParkingLotManagementServer.repositories.taxes.VehiclePriceRepository;
import com.basware.ParkingLotManagementServer.repositories.taxes.discounts.UserDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ParkingSpotPriceRepository parkingSpotPriceRepository;
    @Autowired
    private UserPriceRepository userPriceRepository;
    @Autowired
    private VehiclePriceRepository vehiclePriceRepository;
    @Autowired
    private UserDiscountRepository userDiscountRepository;

    @Override
    public void run(String... args) {
        parkingSpotPriceRepository.deleteAll();
        userPriceRepository.deleteAll();
        vehiclePriceRepository.deleteAll();
        userDiscountRepository.deleteAll();
        loadPricesForUsers();
        loadPricesForParkingSpots();
        loadPricesForVehicles();
        loadUserDiscounts();
    }

    private void loadUserDiscounts() {
        userDiscountRepository.save(new UserDiscount(UserType.REGULAR, new Price(0.25, Currency.EUR)));
        userDiscountRepository.save(new UserDiscount(UserType.VIP, new Price(0.50, Currency.EUR)));
    }

    private void loadPricesForVehicles() {
        vehiclePriceRepository.save(new VehiclePrice(VehicleType.MOTORCYCLE, new Price(0.5, Currency.EUR)));
        vehiclePriceRepository.save(new VehiclePrice(VehicleType.CAR, new Price(0.15, Currency.EUR)));
        vehiclePriceRepository.save(new VehiclePrice(VehicleType.TRUCK, new Price(0.20, Currency.EUR)));
    }

    private void loadPricesForParkingSpots() {
        parkingSpotPriceRepository.save(new ParkingSpotPrice(ParkingSpotType.SMALL, new Price(0.1, Currency.EUR)));
        parkingSpotPriceRepository.save(new ParkingSpotPrice(ParkingSpotType.MEDIUM, new Price(0.2, Currency.EUR)));
        parkingSpotPriceRepository.save(new ParkingSpotPrice(ParkingSpotType.LARGE, new Price(0.3, Currency.EUR)));
    }

    private void loadPricesForUsers() {
        userPriceRepository.save(new UserPrice(UserType.VIP, new Price(0.5, Currency.EUR)));
        userPriceRepository.save(new UserPrice(UserType.REGULAR, new Price(1.0, Currency.EUR)));
    }
}

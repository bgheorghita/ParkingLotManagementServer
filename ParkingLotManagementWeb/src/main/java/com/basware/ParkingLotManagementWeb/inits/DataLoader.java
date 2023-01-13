package com.basware.ParkingLotManagementWeb.inits;

import com.basware.ParkingLotManagementCommon.models.parking.spots.*;
import com.basware.ParkingLotManagementCommon.models.taxes.*;
import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.*;
import com.basware.ParkingLotManagementWeb.repositories.taxes.*;
import com.basware.ParkingLotManagementWeb.services.spots.ParkingSpotService;
import com.basware.ParkingLotManagementWeb.services.tickets.TicketService;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
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

    @Autowired
    private ParkingSpotService parkingSpotService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TicketService ticketService;


    @Override
    public void run(String... args) {
        typePriceDao.deleteAll();
        userTypeDiscountPercentDao.deleteAll();
        loadPricesForParkingSpots();
        loadPricesForUsers();
        loadPricesForVehicles();
        loadUserDiscounts();
        loadParkingSpotsWithElectricCharger();
        loadParkingSpotsWithoutElectricCharger();
    }

    private void loadParkingSpotsWithoutElectricCharger() {
        parkingSpotService.save(new ParkingSpot(ParkingSpotType.SMALL, false));
        parkingSpotService.save(new ParkingSpot(ParkingSpotType.MEDIUM, false));
        parkingSpotService.save(new ParkingSpot(ParkingSpotType.LARGE, false));
        parkingSpotService.save(new ParkingSpot(ParkingSpotType.LARGE, false));
    }

    private void loadParkingSpotsWithElectricCharger() {
        parkingSpotService.save(new ParkingSpot(ParkingSpotType.SMALL, true));
        parkingSpotService.save(new ParkingSpot(ParkingSpotType.MEDIUM, true));
        parkingSpotService.save(new ParkingSpot(ParkingSpotType.LARGE, true));
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

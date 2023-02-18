package com.basware.ParkingLotManagementWeb.inits;

import com.basware.ParkingLotManagementCommon.models.parking.spots.*;
import com.basware.ParkingLotManagementCommon.models.taxes.*;
import com.basware.ParkingLotManagementCommon.models.taxes.discounts.UserDiscount;
import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.basware.ParkingLotManagementCommon.models.vehicles.*;
import com.basware.ParkingLotManagementWeb.exceptions.SaveException;
import com.basware.ParkingLotManagementWeb.exceptions.TooManyRequestsException;
import com.basware.ParkingLotManagementWeb.repositories.taxes.*;
import com.basware.ParkingLotManagementWeb.services.parking.spots.ParkingSpotService;
import com.basware.ParkingLotManagementWeb.services.parking.strategies.CustomParkingStrategyService;
import com.basware.ParkingLotManagementWeb.services.tickets.TicketService;
import com.basware.ParkingLotManagementWeb.services.users.UserService;
import com.basware.ParkingLotManagementWeb.services.vehicles.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ConcurrentModificationException;


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

    @Autowired
    private CustomParkingStrategyService customParkingStrategyService;

    @Override
    public void run(String... args) throws TooManyRequestsException {
        typePriceDao.deleteAll();
        userTypeDiscountPercentDao.deleteAll();
        loadPricesForParkingSpots();
        loadPricesForUsers();
        loadPricesForVehicles();
        loadUserDiscounts();
//        loadParkingSpotsWithElectricCharger();
//        loadParkingSpotsWithoutElectricCharger();
    }

    private void loadParkingSpotsWithoutElectricCharger() throws TooManyRequestsException {
        try {
            parkingSpotService.save(new ParkingSpot(ParkingSpotType.SMALL, 1L, false));
            parkingSpotService.save(new ParkingSpot(ParkingSpotType.MEDIUM, 2L, false));
            parkingSpotService.save(new ParkingSpot(ParkingSpotType.LARGE, 3L, false));
            parkingSpotService.save(new ParkingSpot(ParkingSpotType.LARGE, 4L, false));
        } catch (ConcurrentModificationException ignored) {System.out.println("Parking spots without electric charger NOT loaded");} catch (
                SaveException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadParkingSpotsWithElectricCharger() {
        try{
            parkingSpotService.save(new ParkingSpot(ParkingSpotType.SMALL, 5L, true));
            parkingSpotService.save(new ParkingSpot(ParkingSpotType.MEDIUM, 6L, true));
            parkingSpotService.save(new ParkingSpot(ParkingSpotType.LARGE, 7L, true));
        } catch (ConcurrentModificationException | TooManyRequestsException | SaveException ignored){System.out.println("Parking spots with electric charger NOT loaded");}
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

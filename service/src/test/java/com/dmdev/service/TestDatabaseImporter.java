package com.dmdev.service;

import com.dmdev.service.dao.CarRepository;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.PersonalInfo;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.Role;
import com.dmdev.service.entity.Status;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.entity.TariffType;
import com.dmdev.service.entity.TypeFuel;
import com.dmdev.service.entity.TypeTransmission;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@UtilityClass
public class TestDatabaseImporter {

    public static final LocalDateTime DATE_REQUEST = LocalDateTime
            .of(2012, 12, 12, 12, 12);
    public static final LocalDateTime DATE_RETURN = LocalDateTime
            .of(2012, 12, 13, 12, 12);
    public static final BigDecimal PRICE_FOR_CHANGE = new BigDecimal(40);
    public static final String LASTNAME_FOR_UPDATE = "Irishkova";


    public static void insertDatabase() {
        Tariff hourlyTariff = saveTariff(TariffType.HOURLY, new BigDecimal(12));
        Tariff dailyTariff = saveTariff( TariffType.DAYTIME, new BigDecimal(23));

        CarCharacteristic characteristicAudi = saveCarCharacteristic(1900,
                TypeFuel.DIESEL, TypeTransmission.MANUAL, LocalDate.of(2010, 1, 1));
        CarCharacteristic characteristicBmw = saveCarCharacteristic( 3200,
                TypeFuel.PETROL, TypeTransmission.AUTOMATIC, LocalDate.of(2012, 1, 1));

        Car audi = saveCar( "Audi", Status.FREE, characteristicAudi);
        Car bmw = saveCar( "BMW", Status.FREE, characteristicBmw);

        User olya = saveUser("olya@gmail.com", "Olya",
                "Korob", "1234", Role.USER);
        User ira = saveUser( "ira@gmail.com", "Ira", "Murina",
                "9876", Role.ADMIN);

        saveRequest( DATE_REQUEST, DATE_RETURN, hourlyTariff, audi, olya);
        saveRequest( DATE_REQUEST, DATE_RETURN, dailyTariff, bmw, ira);
    }

    public static User getUser() {
        return User.builder()
                .username("olya22@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Korob")
                        .firstname("Olya")
                        .build())
                .password("1234")
                .role(Role.USER)
                .build();
    }

    public static Car getCar() {
        return Car.builder()
                .model("Audi")
                .status(Status.FREE)
                .build();
    }

    public static CarCharacteristic getCarCharacteristic() {
        return CarCharacteristic.builder()
                .car(getCar())
                .engineVolume(2100)
                .type(TypeFuel.DIESEL)
                .transmission(TypeTransmission.MANUAL)
                .dateRelease(LocalDate.of(2002, 1, 1))
                .build();
    }

    public static Tariff getTariff() {
        return Tariff.builder()
                .price(new BigDecimal(12))
                .type(TariffType.HOURLY)
                .build();
    }

    private Tariff saveTariff(TariffType type,
                              BigDecimal price) {
        Tariff tariff = Tariff.builder()
                .price(price)
                .type(type)
                .build();
        return tariff;
    }

    private User saveUser(String username,
                          String firstname,
                          String lastname,
                          String password,
                          Role role) {
        return User.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .build())
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    private Car saveCar(String model,
                        Status status,
                        CarCharacteristic carCharacteristic) {
        Car car = Car.builder()
                .model(model)
                .status(status)
                .build();
        addCarCharacteristic(carCharacteristic, car);
        return car;
    }

    private CarCharacteristic saveCarCharacteristic(Integer engineVolume,
                                                    TypeFuel type,
                                                    TypeTransmission transmission,
                                                    LocalDate dateRelease) {
        CarCharacteristic carCharacteristic = CarCharacteristic.builder()
                .engineVolume(engineVolume)
                .type(type)
                .transmission(transmission)
                .dateRelease(dateRelease)
                .build();
        return carCharacteristic;
    }

    private void addCarCharacteristic(CarCharacteristic carCharacteristic, Car car) {
        carCharacteristic.setCar(car);
    }

    private void saveRequest(LocalDateTime dateRequest,
                             LocalDateTime dateReturn,
                             Tariff tariff,
                             Car car,
                             User user) {
        Request request = Request.builder()
                .tariff(tariff)
                .car(car)
                .user(user)
                .dateReturn(dateReturn)
                .dateRequest(dateRequest)
                .build();
    }
}

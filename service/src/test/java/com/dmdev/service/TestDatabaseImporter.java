package com.dmdev.service;

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
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

    public static void insertDatabase(SessionFactory sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Tariff hourlyTariff = saveTariff(session, TariffType.HOURLY, new BigDecimal(12));
        Tariff dailyTariff = saveTariff(session, TariffType.DAYTIME, new BigDecimal(23));

        CarCharacteristic characteristicAudi = saveCarCharacteristic(session, 1900,
                TypeFuel.DIESEL, TypeTransmission.MANUAL, LocalDate.of(2010, 1, 1));
        CarCharacteristic characteristicBmw = saveCarCharacteristic(session, 3200,
                TypeFuel.PETROL, TypeTransmission.AUTOMATIC, LocalDate.of(2012, 1, 1));

        Car audi = saveCar(session, "Audi", Status.FREE, characteristicAudi);
        Car bmw = saveCar(session, "BMW", Status.FREE, characteristicBmw);

        User olya = saveUser(session, "olya@gmail.com", "Olya",
                "Korob", "1234", Role.USER);
        User ira = saveUser(session, "ira@gmail.com", "Ira", "Murina",
                "9876", Role.ADMIN);

        saveRequest(session, DATE_REQUEST, DATE_RETURN, hourlyTariff, audi, olya);
        saveRequest(session, DATE_REQUEST, DATE_RETURN, dailyTariff, bmw, ira);

        session.getTransaction().commit();
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

    private Tariff saveTariff(Session session,
                              TariffType type,
                              BigDecimal price) {
        Tariff tariff = Tariff.builder()
                .price(price)
                .type(type)
                .build();
        session.save(tariff);
        return tariff;
    }

    private User saveUser(Session session,
                          String username,
                          String firstname,
                          String lastname,
                          String password,
                          Role role) {
        User user = User.builder()
                .personalInfo(PersonalInfo.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .build())
                .username(username)
                .password(password)
                .role(role)
                .build();
        session.save(user);
        return user;
    }

    private Car saveCar(Session session,
                        String model,
                        Status status,
                        CarCharacteristic carCharacteristic) {
        Car car = Car.builder()
                .model(model)
                .status(status)
                .build();
        addCarCharacteristic(carCharacteristic, car);
        session.save(car);
        return car;
    }

    private CarCharacteristic saveCarCharacteristic(Session session,
                                                    Integer engineVolume,
                                                    TypeFuel type,
                                                    TypeTransmission transmission,
                                                    LocalDate dateRelease) {
        CarCharacteristic carCharacteristic = CarCharacteristic.builder()
                .engineVolume(engineVolume)
                .type(type)
                .transmission(transmission)
                .dateRelease(dateRelease)
                .build();
        session.save(carCharacteristic);
        return carCharacteristic;
    }

    private void addCarCharacteristic(CarCharacteristic carCharacteristic, Car car) {
        carCharacteristic.setCar(car);
    }

    private void saveRequest(Session session,
                             LocalDateTime dateRequest,
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
        session.save(request);
    }
}

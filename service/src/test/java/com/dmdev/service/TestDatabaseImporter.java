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
}

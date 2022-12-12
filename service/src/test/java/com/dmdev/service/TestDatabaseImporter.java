package com.dmdev.service;

import com.dmdev.service.dto.CarCharacteristicReadDto;
import com.dmdev.service.dto.CarCreateEditDto;
import com.dmdev.service.dto.CarReadDto;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.PersonalInfo;
import com.dmdev.service.entity.Role;
import com.dmdev.service.entity.Status;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.entity.TariffType;
import com.dmdev.service.entity.TypeFuel;
import com.dmdev.service.entity.TypeTransmission;
import com.dmdev.service.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


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
                .id(1L)
                .model("Audi")
                .status(Status.FREE)
                .carCharacteristic(CarCharacteristic.builder()
                        .dateRelease(LocalDate.now())
                        .transmission(TypeTransmission.MANUAL)
                        .type(TypeFuel.DIESEL)
                        .engineVolume(1900)
                        .build())
                .image("image")
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

    public static List<Car> getCars() {
        return List.of(getCar());
    }

    public static List<CarReadDto> getCarsDto() {
        return List.of(CarReadDto.builder()
                .carCharacteristic(CarCharacteristicReadDto.builder()
                        .id(1L)
                        .type(TypeFuel.DIESEL)
                        .transmission(TypeTransmission.MANUAL)
                        .dateRelease(LocalDate.now())
                        .build())
                .model("AUDI")
                .status(Status.USED)
                .image("image")
                .build());
    }

    public static CarReadDto getCarReadDto() {
        return CarReadDto.builder()
                .carCharacteristic(CarCharacteristicReadDto.builder().build())
                .id(1L)
                .image("image")
                .model("AUDI")
                .status(Status.FREE)
                .build();
    }

    public static CarCreateEditDto getCarCreateEditDto() {
        return CarCreateEditDto.builder()
                .dateRelease(LocalDate.now())
                .engineVolume(1900)
                .image(new MockMultipartFile("image", new byte[0]))
                .model("BMW")
                .status(Status.FREE)
                .transmission(TypeTransmission.MANUAL)
                .type(TypeFuel.DIESEL)
                .build();
    }
}

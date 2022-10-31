package com.dmdev.service.dao;

import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.Status;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class CarRepositoryIT {

    private final CarRepository carRepository;

    @Transactional
    @Test
    void checkSaveCar() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);

        carRepository.save(car);

        assertThat(car.getId()).isNotNull();
    }

    @Transactional
    @Test
    void checkUpdateCar() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        car.setStatus(Status.USED);

        carRepository.update(car);
        Optional<Car> actual = carRepository.findById(car.getId());

        assertEquals(car.getStatus(), actual.get().getStatus());
    }

    @Transactional
    @Test
    void checkDeleteCar() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);

        carRepository.delete(car);
        Optional<Car> actual = carRepository.findById(car.getId());

        assertThat(actual).isEmpty();
    }

    @Transactional
    @Test
    void checkFindCarBYId() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);

        Optional<Car> mayBeCar = carRepository.findById(car.getId());

        assertThat(mayBeCar.get().getId()).isNotNull();
        assertEquals(car.getId(), mayBeCar.get().getId());
    }

    @Transactional
    @Test
    void checkFindAll() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);

        List<Car> cars = carRepository.findAll();

        assertThat(cars).hasSize(1);
    }

    @Transactional
    @Test
    void checkFindCarByFilter() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        FilterCar filterCar = FilterCar.builder()
                .model(List.of("BMW", "Audi"))
                .build();

        List<Car> carByFilter = carRepository.findCarByFilter(filterCar);

        assertThat(carByFilter).hasSize(1);
    }

    @Transactional
    @Test
    void checkFindCarByStatus() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);

        List<Car> carByStatus = carRepository.findCarByStatus(Status.FREE);

        assertThat(carByStatus).hasSize(1);
    }
}


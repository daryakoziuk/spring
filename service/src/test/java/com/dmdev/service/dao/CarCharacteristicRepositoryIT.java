package com.dmdev.service.dao;

import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.TypeFuel;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class CarCharacteristicRepositoryIT {

    private final CarRepository carRepository;
    private final CarCharacteristicRepository carCharacteristicRepository;
    private final EntityManager entityManager;

    @Transactional
    @Test
    void checkSaveCarCharacteristic() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);

        carRepository.save(car);

        assertThat(carCharacteristic.getId()).isNotNull();
    }

    @Transactional
    @Test
    void checkUpdateCarCharacteristic() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);
        carRepository.save(car);
        Optional<CarCharacteristic> characteristic = carCharacteristicRepository.findById(carCharacteristic.getId());
        characteristic.get().setType(TypeFuel.PETROL);

        carCharacteristicRepository.update(characteristic.get());
        Optional<CarCharacteristic> actual = carCharacteristicRepository.findById(carCharacteristic.getId());

        assertEquals(characteristic.get().getType(), actual.get().getType());
    }

    @Transactional
    @Test
    void checkDeleteCarCharacteristic() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);
        carRepository.save(car);

        carRepository.delete(car);
        var actual = carCharacteristicRepository.findById(carCharacteristic.getId());

        assertThat(actual).isEmpty();
    }

    @Transactional
    @Test
    void checkFindCarCharacteristicById() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);
        carRepository.save(car);

        Optional<CarCharacteristic> mayBeCharacteristic = carCharacteristicRepository.findById(carCharacteristic.getId());

        assertThat(mayBeCharacteristic.get().getId()).isNotNull();
    }

    @Transactional
    @Test
    void checkFindAll() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);
        carRepository.save(car);
        entityManager.clear();

        List<CarCharacteristic> characteristics = carCharacteristicRepository.findAll();

        assertThat(characteristics).hasSize(1);
    }
}

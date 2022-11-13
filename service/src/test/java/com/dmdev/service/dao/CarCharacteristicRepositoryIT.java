package com.dmdev.service.dao;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.annotation.IT;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.TypeFuel;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
@RequiredArgsConstructor
public class CarCharacteristicRepositoryIT extends IntegrationTestBase {

    private final CarRepository carRepository;
    private final CarCharacteristicRepository carCharacteristicRepository;

    @Test
    void checkSaveCarCharacteristic() {
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);

        carRepository.save(car);

        assertThat(carCharacteristic.getId()).isNotNull();
    }

    @Test
    void checkUpdateCarCharacteristic() {
        Optional<CarCharacteristic> characteristic = carCharacteristicRepository.findById(1L);
        characteristic.get().setType(TypeFuel.DIESEL);

        carCharacteristicRepository.updateCarCharacteristic(1L, characteristic.get().getType());
        Optional<CarCharacteristic> actual = carCharacteristicRepository.findById(1L);

        assertThat(characteristic).isPresent();
        assertEquals(characteristic.get().getType(), actual.get().getType());
    }

    @Test
    void checkDeleteCarCharacteristic() {
        Optional<CarCharacteristic> carCharacteristic = carCharacteristicRepository.findById(1L);

        carCharacteristicRepository.delete(carCharacteristic.get());
        var actual = carCharacteristicRepository.findById(1L);

        assertThat(actual).isEmpty();
    }

    @Test
    void checkFindCarCharacteristicById() {
        Optional<CarCharacteristic> mayBeCharacteristic = carCharacteristicRepository.findById(1L);

        assertThat(mayBeCharacteristic).isPresent();
        assertThat(mayBeCharacteristic.get().getId()).isNotNull();
    }

    @Test
    void checkFindAll() {
        List<CarCharacteristic> characteristics = carCharacteristicRepository.findAll();

        assertThat(characteristics).hasSize(4);
    }
}

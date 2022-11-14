package com.dmdev.service.dao;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.Status;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CarRepositoryIT extends IntegrationTestBase {

    private final CarRepository carRepository;

    @Test
    void checkFindCarBYId() {
        Optional<Car> mayBeCar = carRepository.findById(1L);

        assertThat(mayBeCar).isPresent();
        assertThat(mayBeCar.get().getId()).isNotNull();
    }

    @Test
    void checkFindAll() {
        List<Car> cars = carRepository.findAll();

        assertThat(cars).hasSize(4);
    }

    @Test
    void checkFindCarByFilter() {
        FilterCar filterCar = FilterCar.builder()
                .model(List.of("BMW", "AUDI"))
                .build();

        List<Car> carByFilter = carRepository.findAllByFilter(filterCar);

        assertThat(carByFilter).hasSize(2);
    }

    @Test
    void checkFindCarByStatus() {
        List<Car> carByStatus = carRepository.findCarByStatus(Status.FREE);

        assertThat(carByStatus).hasSize(3);
    }
}


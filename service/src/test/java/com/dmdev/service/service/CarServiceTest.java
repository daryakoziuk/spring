package com.dmdev.service.service;

import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.dao.CarRepository;
import com.dmdev.service.dto.CarCreateEditDto;
import com.dmdev.service.dto.CarReadDto;
import com.dmdev.service.dto.predicate.CarPredicateBuilder;
import com.dmdev.service.entity.Car;
import com.dmdev.service.mapper.CarCreateEditMapper;
import com.dmdev.service.mapper.Mapper;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    private static final Long CAR_ID = 1L;
    private final Car car = TestDatabaseImporter.getCar();
    private final List<CarReadDto>carsDto = TestDatabaseImporter.getCarsDto();
    private final CarReadDto carReadDto = TestDatabaseImporter.getCarReadDto();
    @Mock
    private CarPredicateBuilder carPredicateBuilder;
    @Mock
    @Getter
    private CarRepository repository;
    @Mock
    @Getter
    private Mapper<Car, CarReadDto> mapper;
    @Mock
    private CarCreateEditMapper carCreateEditMapper;
    @Mock
    private ImageService imageService;
    @InjectMocks
    private CarService carService;

    @Test
    void findAll() {
        List<Car> cars = List.of(car);
        when(getRepository().findAll()).thenReturn(cars);
        doReturn(carsDto).when(getMapper()).map(any());

        List<CarReadDto> actual = carService.findAll();

        assertEquals(1, actual.size());
        verify(getRepository(), times(1)).findAll();
    }

    @Test
    void findById() {
        when(getRepository().findById(CAR_ID)).thenReturn(Optional.ofNullable(car));
        when(getMapper().map(any())).thenReturn(carReadDto);

        Optional<CarReadDto> actual = carService.findById(1L);

        assertThat(actual.get().getId()).isNotNull();
    }

    @Test
    void update() {
        CarCreateEditDto carCreateEditDto = CarCreateEditDto.builder().build();
        when(repository.findById(CAR_ID)).thenReturn(Optional.ofNullable(TestDatabaseImporter.getCar()));
        when(carCreateEditMapper.map(carCreateEditDto, TestDatabaseImporter.getCar())).thenReturn(TestDatabaseImporter.getCar());
        doReturn(TestDatabaseImporter.getCar()).when(repository).saveAndFlush(TestDatabaseImporter.getCar());
        when(mapper.map(TestDatabaseImporter.getCar())).thenReturn(TestDatabaseImporter.getCarReadDto());

        Optional<CarReadDto> carReadDto = carService.update(CAR_ID, TestDatabaseImporter.getCarCreateEditDto());

        assertThat(carReadDto.get().getModel()).isEqualTo(TestDatabaseImporter.getCarReadDto().getModel());
    }

    @Test
    void create() {
    }

    @Test
    void findImage() {
    }

    @Test
    void getCarPredicateBuilder() {
    }

    @Test
    void getCarCreateEditMapper() {
    }

    @Test
    void getImageService() {
    }
}
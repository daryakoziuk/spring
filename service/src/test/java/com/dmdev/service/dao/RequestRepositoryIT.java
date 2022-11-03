package com.dmdev.service.dao;

import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.dmdev.service.TestDatabaseImporter.DATE_REQUEST;
import static com.dmdev.service.TestDatabaseImporter.DATE_RETURN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class RequestRepositoryIT {

    private final RequestRepository requestRepository;
    private final TariffRepository tariffRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final EntityManager entityManager;

    @Transactional
    @Test
    void checkSaveRequest() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        User user = TestDatabaseImporter.getUser();
        userRepository.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);

        requestRepository.save(request);

        assertThat(request.getId()).isNotNull();
    }

    @Transactional
    @Test
    void checkUpdateRequest() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        User user = TestDatabaseImporter.getUser();
        userRepository.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);
        requestRepository.save(request);
        request.setDateReturn(LocalDateTime.of(22, 12, 22, 15, 0));

        requestRepository.update(request);
        Optional<Request> actual = requestRepository.findById(request.getId());

        assertEquals(request.getDateReturn(), actual.get().getDateReturn());
    }

    @Transactional
    @Test
    void checkDeleteRequest() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        User user = TestDatabaseImporter.getUser();
        userRepository.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);
        requestRepository.save(request);
        entityManager.flush();
        entityManager.clear();
        Optional<Request> forDelete = requestRepository.findById(request.getId());

        requestRepository.delete(forDelete.get());
        Optional<Request> actual = requestRepository.findById(request.getId());

        assertThat(actual).isEmpty();
    }

    @Transactional
    @Test
    void checkFindRequestById() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        User user = TestDatabaseImporter.getUser();
        userRepository.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);
        requestRepository.save(request);

        Optional<Request> mayBeRequest = requestRepository.findById(request.getId());

        assertThat(mayBeRequest.get().getId()).isNotNull();
        assertEquals(request.getId(), mayBeRequest.get().getId());
    }

    @Transactional
    @Test
    void checkFindAll() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        User user = TestDatabaseImporter.getUser();
        userRepository.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);
        requestRepository.save(request);

        List<Request> requests = requestRepository.findAll();

        assertThat(requests).hasSize(1);
    }
}

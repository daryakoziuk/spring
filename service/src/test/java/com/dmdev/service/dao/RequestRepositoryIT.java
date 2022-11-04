package com.dmdev.service.dao;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.annotation.IT;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.dmdev.service.TestDatabaseImporter.DATE_REQUEST;
import static com.dmdev.service.TestDatabaseImporter.DATE_RETURN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
@RequiredArgsConstructor
public class RequestRepositoryIT extends IntegrationTestBase {

    private final RequestRepository requestRepository;
    private final TariffRepository tariffRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final EntityManager entityManager;

    @Test
    void checkSaveRequest() {
        Optional<Tariff> tariff = tariffRepository.findById(1);
        Optional<Car> car = carRepository.findById(1L);
        Optional<User> user = userRepository.findById(1L);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user.get());
        request.setTariff(tariff.get());
        request.setCar(car.get());

        requestRepository.save(request);

        assertThat(request.getId()).isNotNull();
    }

    @Test
    void checkUpdateRequest() {
        Optional<Request> request = requestRepository.findById(1L);
        request.get().setDateReturn(LocalDateTime.of(22, 12, 22, 15, 0));

        requestRepository.updateDateReturn(1L, request.get().getDateReturn());
        Optional<Request> actual = requestRepository.findById(1L);

        assertThat(request).isPresent();
        assertEquals(request.get().getDateReturn(), actual.get().getDateReturn());
    }

    @Test
    void checkDeleteRequest() {
        Optional<Request> request = requestRepository.findById(1L);

        requestRepository.delete(request.get());
        entityManager.flush();
        entityManager.clear();
        Optional<Request> actual = requestRepository.findById(1L);

        assertThat(actual).isEmpty();
    }

    @Test
    void checkFindRequestById() {
        Optional<Request> mayBeRequest = requestRepository.findById(2L);

        assertThat(mayBeRequest).isPresent();
    }

    @Test
    void checkFindAll() {
        List<Request> requests = requestRepository.findAll();

        assertThat(requests).hasSize(5);
    }
}

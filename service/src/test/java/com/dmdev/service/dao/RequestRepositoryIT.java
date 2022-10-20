package com.dmdev.service.dao;

import com.dmdev.service.HibernateTestUtil;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.dmdev.service.TestDatabaseImporter.DATE_REQUEST;
import static com.dmdev.service.TestDatabaseImporter.DATE_RETURN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestRepositoryIT {

    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final RequestRepository requestRepository;
    private final TariffRepository tariffRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public RequestRepositoryIT(RequestRepository requestRepository,
                               TariffRepository tariffRepository,
                               UserRepository userRepository,
                               CarRepository carRepository) {
        this.requestRepository = requestRepository;
        this.tariffRepository = tariffRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @BeforeAll
    static void initDb() {
        TestDatabaseImporter.insertDatabase(sessionFactory);
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @Test
    void checkSaveRequest() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
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
        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateRequest() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
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
        session.flush();
        session.clear();
        Request actual = session.find(Request.class, request.getId());

        assertEquals(request.getDateReturn(), actual.getDateReturn());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteRequest() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
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
        session.clear();

        requestRepository.delete(request);
        session.flush();
        session.clear();
        Request actual = session.find(Request.class, request.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkFindRequestById() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
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
        session.getTransaction().rollback();
    }

    @Test
    void checkFindAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Request> requests = requestRepository.findAll();

        assertThat(requests).hasSize(2);
        session.getTransaction().rollback();
    }
}

package com.dmdev.service.dao;

import com.dmdev.service.HibernateTestUtil;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarRepositoryIT {

    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final CarRepository carRepository = new CarRepository(sessionFactory.getCurrentSession());

    @BeforeAll
    static void initDb() {
        TestDatabaseImporter.insertDatabase(sessionFactory);
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @Test
    void checkSaveCar() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);

        carRepository.save(car);

        assertThat(car.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateCar() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        session.clear();
        car.setStatus(Status.USED);

        carRepository.update(car);
        session.flush();
        session.clear();
        Car actual = session.get(Car.class, car.getId());

        assertEquals(car.getStatus(), actual.getStatus());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteCar() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        session.clear();

        carRepository.delete(car.getId());
        session.flush();
        Car actual = session.get(Car.class, car.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkFindCarBYId() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        carRepository.save(car);
        session.clear();

        Optional<Car> mayBeCar = carRepository.findById(car.getId());

        assertThat(mayBeCar.get().getId()).isNotNull();
        assertEquals(car.getId(), mayBeCar.get().getId());
        session.getTransaction().rollback();
    }

    @Test
    void checkFindAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Car> cars = carRepository.findAll();

        assertThat(cars).hasSize(2);
        session.getTransaction().rollback();
    }

    @Test
    void checkFindCarByFilter() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        FilterCar filterCar = FilterCar.builder()
                .model(List.of("BMW", "Audi"))
                .build();

        List<Car> carByFilter = carRepository.findCarByFilter(filterCar);

        assertThat(carByFilter).hasSize(2);
        session.getTransaction().rollback();
    }

    @Test
    void checkFindCarByStatus() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Car> carByStatus = carRepository.findCarByStatus(Status.FREE);

        assertThat(carByStatus).hasSize(2);
        session.getTransaction().rollback();
    }
}


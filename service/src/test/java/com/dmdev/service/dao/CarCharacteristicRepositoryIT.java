package com.dmdev.service.dao;

import com.dmdev.service.HibernateTestUtil;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.TypeFuel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarCharacteristicRepositoryIT {

    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final CarCharacteristicRepository carCharacteristicRepository;
    private final CarRepository carRepository;

    public CarCharacteristicRepositoryIT(CarCharacteristicRepository carCharacteristicRepository,
                                         CarRepository carRepository) {
        this.carCharacteristicRepository = carCharacteristicRepository;
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
    void checkSaveCarCharacteristic() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);
        carRepository.save(car);
        session.clear();

        assertThat(carCharacteristic.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateCarCharacteristic() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);
        carRepository.save(car);
        session.clear();
        CarCharacteristic update = session.find(CarCharacteristic.class, carCharacteristic.getId());
        update.setType(TypeFuel.PETROL);

        carCharacteristicRepository.update(update);
        session.flush();
        session.clear();
        CarCharacteristic actual = session.find(CarCharacteristic.class, carCharacteristic.getId());

        assertEquals(update.getType(), actual.getType());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteCarCharacteristic() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);
        carRepository.save(car);
        session.clear();

        carCharacteristicRepository.delete(carCharacteristic);
        var actual = session.find(CarCharacteristic.class, carCharacteristic.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkFindCarCharacteristicById() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        car.setCarCharacteristic(carCharacteristic);
        carRepository.save(car);
        session.clear();

        Optional<CarCharacteristic> mayBeCharacteristic = carCharacteristicRepository.findById(carCharacteristic.getId());

        assertThat(mayBeCharacteristic.get().getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkFindAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<CarCharacteristic> characteristics = carCharacteristicRepository.findAll();

        assertThat(characteristics).hasSize(2);
        session.getTransaction().rollback();
    }
}

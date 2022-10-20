package com.dmdev.service.integration;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarCharacteristicIT {

    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

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
        carCharacteristic.setCar(car);
        session.save(car);

        assertThat(car.getId()).isNotNull();

        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateCarCharacteristic() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        session.clear();
        CarCharacteristic carCharacteristic2 = session.find(CarCharacteristic.class, carCharacteristic.getId());
        carCharacteristic2.setType(TypeFuel.PETROL);

        session.merge(carCharacteristic2);
        session.flush();
        session.clear();
        CarCharacteristic actual = session.get(CarCharacteristic.class, carCharacteristic2.getId());

        assertEquals(carCharacteristic2.getType(), actual.getType());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteCarCharacteristic() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        session.clear();

        session.delete(carCharacteristic);
        session.flush();
        var actual = session.get(CarCharacteristic.class, carCharacteristic.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkGetCarCharacteristic() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        session.clear();

        CarCharacteristic actual = session.get(CarCharacteristic.class, carCharacteristic.getId());

        assertThat(actual.getId()).isNotNull();
        session.getTransaction().rollback();
    }
}


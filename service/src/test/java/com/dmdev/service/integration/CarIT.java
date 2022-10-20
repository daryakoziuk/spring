package com.dmdev.service.integration;

import com.dmdev.service.HibernateTestUtil;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.dto.predicate.QPredicate;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.Status;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.dmdev.service.entity.QCar.car;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarIT {

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
    void checkQuerydslWithFilter() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        FilterCar filterCar = FilterCar.builder()
                .model(List.of("BMW", "Audi"))
                .dateRelease(LocalDate.of(2012, 1, 1))
                .build();
        Predicate predicate = QPredicate.builder()
                .add(filterCar.getDateRelease(), car.carCharacteristic.dateRelease::eq)
                .add(filterCar.getModel(), car.model::in)
                .buildAll();

        List<Car> cars = new JPAQuery<Request>(session)
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();

        assertThat(cars).hasSize(1);
        session.getTransaction().rollback();
    }

    @Test
    void checkSaveCar() {
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
    void checkUpdateCar() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        session.clear();
        Car carUpdate = session.find(Car.class, car.getId());
        carUpdate.setStatus(Status.USED);

        session.merge(car);
        session.flush();
        session.clear();
        Car actual = session.find(Car.class, car.getId());

        assertEquals(carUpdate.getStatus(), actual.getStatus());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteCar() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        session.clear();

        session.delete(car);
        session.flush();
        Car actual = session.get(Car.class, car.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkGetCar() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        session.clear();

        Car actual = session.find(Car.class, car.getId());

        assertThat(actual.getId()).isNotNull();
        session.getTransaction().rollback();
    }
}

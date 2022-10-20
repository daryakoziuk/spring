package com.dmdev.service.integration;

import com.dmdev.service.HibernateTestUtil;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.dto.FilterUser;
import com.dmdev.service.dto.predicate.PredicateCriteria;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.Request_;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

import static com.dmdev.service.TestDatabaseImporter.DATE_REQUEST;
import static com.dmdev.service.TestDatabaseImporter.DATE_RETURN;
import static com.dmdev.service.entity.PersonalInfo_.FIRSTNAME;
import static com.dmdev.service.entity.PersonalInfo_.LASTNAME;
import static com.dmdev.service.entity.User_.PERSONAL_INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestIT {

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
    void checkCriteria() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        FilterUser filterUser = FilterUser.builder()
                .firstname("Olya")
                .lastname("Korob")
                .build();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Request> criteria = cb.createQuery(Request.class);
        Root<Request> request = criteria.from(Request.class);
        Join<Request, User> user = request.join(Request_.user);

        List<javax.persistence.criteria.Predicate> predicates = PredicateCriteria.builder()
                .add(filterUser.getFirstname(), firstname -> cb.equal(user.get(PERSONAL_INFO)
                        .get(FIRSTNAME), firstname))
                .add(filterUser.getLastname(), lastname -> cb.equal(user.get(PERSONAL_INFO)
                        .get(LASTNAME), lastname))
                .getPredicates();

        criteria.select(request).where(predicates.toArray(javax.persistence.criteria.Predicate[]::new));
        List<Request> actual = session.createQuery(criteria).list();

        assertThat(actual).hasSize(1);
        session.getTransaction().rollback();
    }

    @Test
    void checkSaveRequest() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        session.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        User user = TestDatabaseImporter.getUser();
        session.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);

        session.persist(request);

        assertThat(request.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateRequest() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        session.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        User user = TestDatabaseImporter.getUser();
        session.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);
        session.persist(request);
        Request requestForUpdate = session.find(Request.class, request.getId());
        requestForUpdate.setDateReturn(LocalDateTime.of(22, 12, 22, 15, 0));

        session.update(requestForUpdate);
        session.flush();
        session.clear();
        Request updateRequest = session.get(Request.class, requestForUpdate.getId());

        assertEquals(requestForUpdate.getDateReturn(), updateRequest.getDateReturn());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteRequest() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        session.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        User user = TestDatabaseImporter.getUser();
        session.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);
        session.persist(request);
        session.clear();

        session.delete(request);
        session.flush();
        session.clear();
        Request actual = session.find(Request.class, request.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkGetRequest() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        session.save(tariff);
        Car car = TestDatabaseImporter.getCar();
        CarCharacteristic carCharacteristic = TestDatabaseImporter.getCarCharacteristic();
        carCharacteristic.setCar(car);
        session.save(car);
        User user = TestDatabaseImporter.getUser();
        session.save(user);
        Request request = Request.builder()
                .dateRequest(DATE_REQUEST)
                .dateReturn(DATE_RETURN)
                .build();
        request.setUser(user);
        request.setTariff(tariff);
        request.setCar(car);
        session.persist(request);
        session.clear();

        Request actual = session.find(Request.class, request.getId());

        assertThat(actual.getId()).isNotNull();
        session.getTransaction().rollback();
    }
}

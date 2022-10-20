package com.dmdev.service.integration;

import com.dmdev.service.HibernateTestUtil;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.dto.FilterUser;
import com.dmdev.service.dto.predicate.PredicateCriteria;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

import static com.dmdev.service.TestDatabaseImporter.LASTNAME_FOR_UPDATE;
import static com.dmdev.service.entity.Car_.REQUESTS;
import static com.dmdev.service.entity.PersonalInfo_.FIRSTNAME;
import static com.dmdev.service.entity.PersonalInfo_.LASTNAME;
import static com.dmdev.service.entity.Request_.USER;
import static com.dmdev.service.entity.User_.PERSONAL_INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserIT {

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
    void checkEntityGraph() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        RootGraph<?> entityGraph = session.getEntityGraph("withRequestAndCar");
        Map<String, Object> properties = Map.of(GraphSemantic.FETCH.getJpaHintName(), entityGraph);

        User user = session.find(User.class, 1L, properties);
        System.out.println(user.getRequests().get(0).getCar());

        assertThat(user.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkCriteria() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        FilterUser filterUser = FilterUser.builder()
                .lastname("Korob")
                .firstname("Olya")
                .build();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Car> criteria = cb.createQuery(Car.class);
        Root<Car> car = criteria.from(Car.class);
        Join<Object, Object> request = car.join(REQUESTS);
        Join<Object, Object> user = request.join(USER);
        List<Predicate> predicates = PredicateCriteria.builder()
                .add(filterUser.getFirstname(), firstname -> cb.equal(user.get(PERSONAL_INFO)
                        .get(FIRSTNAME), firstname))
                .add(filterUser.getLastname(), lastname -> cb.equal(user.get(PERSONAL_INFO)
                        .get(LASTNAME), lastname))
                .getPredicates();
        criteria.select(car).where(predicates.toArray(Predicate[]::new));
        List<Car> cars = session.createQuery(criteria).list();

        assertThat(cars).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkSaveUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = TestDatabaseImporter.getUser();

        session.save(user);

        assertThat(user.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = TestDatabaseImporter.getUser();
        session.save(user);
        session.clear();
        User userForUpdate = session.find(User.class, user.getId());
        userForUpdate.getPersonalInfo().setLastname(LASTNAME_FOR_UPDATE);

        session.merge(userForUpdate);
        session.flush();
        session.clear();
        User actualUser = session.get(User.class, userForUpdate.getId());

        assertEquals(userForUpdate.getPersonalInfo().getLastname(), actualUser.getPersonalInfo().getLastname());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = TestDatabaseImporter.getUser();
        session.save(user);
        session.clear();

        session.delete(user);
        session.flush();
        User actual = session.get(User.class, user.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkGetUser() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = TestDatabaseImporter.getUser();
        session.save(user);
        session.clear();
        session.clear();

        User actual = session.get(User.class, user.getId());

        assertThat(actual.getId()).isNotNull();
        session.getTransaction().rollback();
    }
}

package com.dmdev.service.integration;

import com.dmdev.service.HibernateTestUtil;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.entity.Tariff;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.dmdev.service.TestDatabaseImporter.PRICE_FOR_CHANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TariffIT {

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
    void checkSaveTariff() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();

        session.save(tariff);

        assertThat(tariff.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateTariff() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        session.save(tariff);
        Tariff forUpdate = session.find(Tariff.class, tariff.getId());
        forUpdate.setPrice(PRICE_FOR_CHANGE);

        session.merge(forUpdate);
        session.flush();
        session.clear();
        Tariff actualTariff = session.find(Tariff.class, forUpdate.getId());

        assertEquals(forUpdate.getPrice(), actualTariff.getPrice());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteTariff() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        session.save(tariff);

        session.delete(tariff);
        session.flush();
        Tariff actual = session.get(Tariff.class, tariff.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkGetTariff() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        session.save(tariff);
        session.clear();

        Tariff actual = session.find(Tariff.class, tariff.getId());

        assertThat(actual.getId()).isNotNull();
        session.getTransaction().rollback();
    }
}

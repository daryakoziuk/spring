package com.dmdev.service.dao;

import com.dmdev.service.HibernateTestUtil;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.entity.Tariff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TariffRepositoryIT {

    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final TariffRepository tariffRepository;

    public TariffRepositoryIT(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
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
    void checkSaveTariff() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();

        Tariff saveTariff = tariffRepository.save(tariff);

        assertThat(saveTariff.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateTariff() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        tariff.setPrice(new BigDecimal(15));

        tariffRepository.update(tariff);
        session.flush();
        session.clear();
        Tariff actualTariff = session.find(Tariff.class, tariff.getId());

        assertEquals(tariff.getPrice(), actualTariff.getPrice());
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteTariff() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        session.flush();
        session.clear();

        tariffRepository.delete(tariff);
        session.flush();
        Tariff actual = session.find(Tariff.class, tariff.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkFindTariffById() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);

        Optional<Tariff> mayBeTariff = tariffRepository.findById(tariff.getId());

        assertThat(mayBeTariff).isPresent();
        assertEquals(tariff.getId(), mayBeTariff.get().getId());
        session.getTransaction().rollback();
    }

    @Test
    void checkFindAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Tariff> tariffs = tariffRepository.findAll();

        assertThat(tariffs).hasSize(2);
        session.getTransaction().rollback();
    }
}


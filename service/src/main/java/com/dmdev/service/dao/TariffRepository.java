package com.dmdev.service.dao;

import com.dmdev.service.entity.Tariff;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class TariffRepository extends BaseRepository<Integer, Tariff> {

    public TariffRepository(EntityManager entityManager) {
        super(entityManager, Tariff.class);
    }
}

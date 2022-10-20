package com.dmdev.service.dao;

import com.dmdev.service.entity.Tariff;

import javax.persistence.EntityManager;

public class TariffRepository extends BaseRepository<Integer, Tariff> {

    public TariffRepository(EntityManager entityManager) {
        super(entityManager, Tariff.class);
    }
}

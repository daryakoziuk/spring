package com.dmdev.service.dao;

import com.dmdev.service.entity.CarCharacteristic;

import javax.persistence.EntityManager;

public class CarCharacteristicRepository extends BaseRepository<Integer, CarCharacteristic> {

    public CarCharacteristicRepository(EntityManager entityManager) {
        super(entityManager, CarCharacteristic.class);
    }
}

package com.dmdev.service.dao;

import com.dmdev.service.entity.CarCharacteristic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CarCharacteristicRepository extends BaseRepository<Integer, CarCharacteristic> {

    public CarCharacteristicRepository(EntityManager entityManager) {
        super(entityManager, CarCharacteristic.class);
    }
}

package com.dmdev.service.dao;

import com.dmdev.service.entity.CarCharacteristic;
import com.dmdev.service.entity.TypeFuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CarCharacteristicRepository extends JpaRepository<CarCharacteristic, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update CarCharacteristic ch set ch.type=:type where ch.id=:id")
    int updateCarCharacteristic(Long id, TypeFuel type);
}

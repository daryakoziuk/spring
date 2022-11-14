package com.dmdev.service.dao;

import com.dmdev.service.entity.CarCharacteristic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarCharacteristicRepository extends JpaRepository<CarCharacteristic, Long> {

}

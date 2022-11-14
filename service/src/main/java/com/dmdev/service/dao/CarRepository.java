package com.dmdev.service.dao;

import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long>, FilterCarRepository {

    @Query("select c from Car c where c.status = :status")
    List<Car> findCarByStatus(Status status);
}

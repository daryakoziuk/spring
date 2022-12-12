package com.dmdev.service.dao;

import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>, QuerydslPredicateExecutor<Car>, FilterCarRepository {

    @Query("select c from Car c where c.status = :status")
    List<Car> findCarByStatus(Status status);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Car c where c.id = :id")
    Optional<Car> findByIdForRequest(Long id);
}

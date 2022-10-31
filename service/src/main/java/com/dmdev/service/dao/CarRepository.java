package com.dmdev.service.dao;

import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.dto.predicate.CarPredicateBuilder;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.Status;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.service.entity.QCar.car;
import static com.dmdev.service.entity.QCarCharacteristic.carCharacteristic;

@Repository
public class CarRepository extends BaseRepository<Long, Car> {

    private final CarPredicateBuilder carPredicate;

    public CarRepository(EntityManager entityManager, CarPredicateBuilder carPredicate) {
        super(entityManager, Car.class);
        this.carPredicate = carPredicate;
    }

    public List<Car> findCarByFilter(FilterCar filterCar) {
        return new JPAQuery<Car>(getEntityManager())
                .select(car)
                .from(car)
                .join(car.carCharacteristic, carCharacteristic)
                .where(carPredicate.builder(filterCar))
                .fetch();
    }

    public List<Car> findCarByStatus(Status status) {
        return new JPAQuery<Car>(getEntityManager())
                .select(car)
                .from(car)
                .where(car.status.eq(status))
                .fetch();
    }
}

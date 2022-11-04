package com.dmdev.service.dao;

import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.dto.predicate.CarPredicateBuilder;
import com.dmdev.service.entity.Car;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.service.entity.QCar.car;

@RequiredArgsConstructor
public class FilterCarRepositoryImpl implements FilterCarRepository {

    private final EntityManager entityManager;
    private final CarPredicateBuilder carPredicateBuilder;

    @Override
    public List<Car> findAllByFilter(FilterCar filterCar) {
        Predicate predicate = carPredicateBuilder.builder(filterCar);
        return new JPAQuery<Car>(entityManager)
                .select(car)
                .from(car)
                .where(predicate)
                .fetch();
    }
}

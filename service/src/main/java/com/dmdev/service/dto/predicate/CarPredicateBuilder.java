package com.dmdev.service.dto.predicate;

import com.dmdev.service.dto.FilterCar;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

import static com.dmdev.service.entity.QCar.car;

@Component
public class CarPredicateBuilder implements PredicateBuilder<Predicate, FilterCar> {

    @Override
    public Predicate builder(FilterCar filter) {
        return QPredicate.builder()
                .add(filter.getType(), car.carCharacteristic.type::in)
                .add(filter.getTransmission(), car.carCharacteristic.transmission::in)
                .add(filter.getModel(), car.model::in)
                .add(filter.getDateRelease(), car.carCharacteristic.dateRelease::after)
                .add(filter.getEngineVolume(), car.carCharacteristic.engineVolume::eq)
                .buildAll();
    }
}

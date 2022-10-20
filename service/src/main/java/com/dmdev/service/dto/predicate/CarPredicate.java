package com.dmdev.service.dto.predicate;

import com.dmdev.service.dto.FilterCar;
import com.querydsl.core.types.Predicate;

import static com.dmdev.service.entity.QCar.car;

public class CarPredicate implements BuilderPredicate<Predicate, FilterCar>{

    @Override
    public Predicate builder(FilterCar filter) {
        return QPredicate.builder()
                .add(filter.getType(), car.carCharacteristic.type::eq)
                .add(filter.getTransmission(), car.carCharacteristic.transmission::eq)
                .add(filter.getModel(), car.model::in)
                .add(filter.getDateRelease(), car.carCharacteristic.dateRelease::eq)
                .add(filter.getEngineVolume(), car.carCharacteristic.engineVolume::eq)
                .buildAll();
    }
}

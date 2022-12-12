package com.dmdev.service.mapper;

import com.dmdev.service.dto.CarCreateEditDto;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.CarCharacteristic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class CarCreateEditMapper implements MapperUpdate<CarCreateEditDto, Car>, Mapper<CarCreateEditDto, Car> {

    @Override
    public Car map(CarCreateEditDto carCreateEditDto, Car toObject) {
        getCar(carCreateEditDto, toObject, toObject.getCarCharacteristic());
        return toObject;
    }

    @Override
    public Car map(CarCreateEditDto carCreateEditDto) {
        Car car = new Car();
        CarCharacteristic carCharacteristic = new CarCharacteristic();
        carCharacteristic.setCar(car);
        getCar(carCreateEditDto, car, carCharacteristic);
        return car;
    }

    private void getCar(CarCreateEditDto carCreateEditDto, Car car, CarCharacteristic carCharacteristic) {
        carCharacteristic.setDateRelease(carCreateEditDto.getDateRelease());
        carCharacteristic.setType(carCreateEditDto.getType());
        carCharacteristic.setEngineVolume(carCreateEditDto.getEngineVolume());
        carCharacteristic.setTransmission(carCreateEditDto.getTransmission());
        Optional.ofNullable(carCreateEditDto.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> car.setImage(image.getOriginalFilename()));
        car.setStatus(carCreateEditDto.getStatus());
        car.setModel(carCreateEditDto.getModel());
        car.setCarCharacteristic(carCharacteristic);
    }
}

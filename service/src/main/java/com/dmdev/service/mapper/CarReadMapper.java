package com.dmdev.service.mapper;

import com.dmdev.service.dto.CarCharacteristicReadDto;
import com.dmdev.service.dto.CarReadDto;
import com.dmdev.service.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarReadMapper implements Mapper<Car, CarReadDto> {

    private final CarCharacteristicReadMapper carCharacteristicReadMapper;

    @Override
    public CarReadDto map(Car car) {
        CarCharacteristicReadDto carCharacteristicReadDto = carCharacteristicReadMapper.map(car.getCarCharacteristic());
        return CarReadDto.builder()
                .id(car.getId())
                .carCharacteristic(carCharacteristicReadDto)
                .model(car.getModel())
                .status(car.getStatus())
                .image(car.getImage())
                .build();
    }
}

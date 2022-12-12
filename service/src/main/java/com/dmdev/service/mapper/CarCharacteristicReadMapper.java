package com.dmdev.service.mapper;

import com.dmdev.service.dto.CarCharacteristicReadDto;
import com.dmdev.service.entity.CarCharacteristic;
import org.springframework.stereotype.Component;

@Component
public class CarCharacteristicReadMapper implements Mapper<CarCharacteristic, CarCharacteristicReadDto> {

    @Override
    public CarCharacteristicReadDto map(CarCharacteristic carCharacteristic) {
        return CarCharacteristicReadDto.builder()
                .id(carCharacteristic.getId())
                .engineVolume(carCharacteristic.getEngineVolume())
                .transmission(carCharacteristic.getTransmission())
                .type(carCharacteristic.getType())
                .dateRelease(carCharacteristic.getDateRelease())
                .build();
    }
}

package com.dmdev.service.mapper;

import com.dmdev.service.dto.CarCharacteristicReadDto;
import com.dmdev.service.entity.CarCharacteristic;
import org.springframework.stereotype.Component;

@Component
public class CarCharacteristicReadMapper implements Mapper<CarCharacteristic, CarCharacteristicReadDto> {

    @Override
    public CarCharacteristicReadDto map(CarCharacteristic carCharacteristic) {
        return new CarCharacteristicReadDto(
                carCharacteristic.getId(),
                carCharacteristic.getEngineVolume(),
                carCharacteristic.getTransmission(),
                carCharacteristic.getType(),
                carCharacteristic.getDateRelease()
        );
    }
}

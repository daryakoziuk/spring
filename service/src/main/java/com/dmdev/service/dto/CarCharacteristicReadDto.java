package com.dmdev.service.dto;

import com.dmdev.service.entity.TypeFuel;
import com.dmdev.service.entity.TypeTransmission;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CarCharacteristicReadDto {

    Long id;
    Integer engineVolume;
    TypeTransmission transmission;
    TypeFuel type;
    LocalDate dateRelease;
}

package com.dmdev.service.dto;

import com.dmdev.service.entity.TypeFuel;
import com.dmdev.service.entity.TypeTransmission;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CarCharacteristicReadDto {

    Long id;
    Integer engineVolume;
    TypeTransmission transmission;
    TypeFuel type;
    LocalDate dateRelease;
}

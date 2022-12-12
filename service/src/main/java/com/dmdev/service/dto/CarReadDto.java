package com.dmdev.service.dto;

import com.dmdev.service.entity.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarReadDto {

    Long id;
    CarCharacteristicReadDto carCharacteristic;
    String model;
    Status status;
    String image;

}

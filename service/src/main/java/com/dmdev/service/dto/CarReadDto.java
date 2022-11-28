package com.dmdev.service.dto;

import com.dmdev.service.entity.Status;
import lombok.Value;

@Value
public class CarReadDto {

    Long id;
    CarCharacteristicReadDto carCharacteristic;
    String model;
    Status status;
    String image;

}

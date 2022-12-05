package com.dmdev.service.dto;

import com.dmdev.service.entity.RequestStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class RequestReadDto {

    Long id;
    LocalDateTime dateRequest;
    LocalDateTime dateReturn;
    TariffReadDto tariff;
    UserReadDto user;
    CarReadDto car;
    RequestStatus status;

}

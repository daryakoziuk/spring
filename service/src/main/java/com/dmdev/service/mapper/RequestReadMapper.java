package com.dmdev.service.mapper;

import com.dmdev.service.dto.CarReadDto;
import com.dmdev.service.dto.RequestReadDto;
import com.dmdev.service.dto.TariffReadDto;
import com.dmdev.service.dto.UserReadDto;
import com.dmdev.service.entity.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestReadMapper implements Mapper<Request, RequestReadDto> {

    private final CarReadMapper carReadMapper;
    private final UserReadMapper userReadMapper;
    private final TariffReadMapper tariffReadMapper;

    @Override
    public RequestReadDto map(Request request) {
        CarReadDto carReadDto = carReadMapper.map(request.getCar());
        UserReadDto userReadDto = userReadMapper.map(request.getUser());
        TariffReadDto tariffReadDto = tariffReadMapper.map(request.getTariff());
        return RequestReadDto.builder()
                .id(request.getId())
                .dateRequest(request.getDateRequest())
                .dateReturn(request.getDateReturn())
                .car(carReadDto)
                .user(userReadDto)
                .tariff(tariffReadDto)
                .status(request.getStatus())
                .build();
    }
}

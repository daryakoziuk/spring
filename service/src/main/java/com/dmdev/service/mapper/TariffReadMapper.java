package com.dmdev.service.mapper;

import com.dmdev.service.dto.TariffReadDto;
import com.dmdev.service.entity.Tariff;
import org.springframework.stereotype.Component;

@Component
public class TariffReadMapper implements Mapper<Tariff, TariffReadDto> {

    @Override
    public TariffReadDto map(Tariff tariff) {
        return TariffReadDto.builder()
                .id(tariff.getId())
                .price(tariff.getPrice())
                .type(tariff.getType())
                .build();
    }
}

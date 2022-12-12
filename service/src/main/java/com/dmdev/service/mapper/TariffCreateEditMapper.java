package com.dmdev.service.mapper;

import com.dmdev.service.dto.TariffCreateEditDto;
import com.dmdev.service.entity.Tariff;
import org.springframework.stereotype.Component;

@Component
public class TariffCreateEditMapper implements Mapper<TariffCreateEditDto, Tariff>,
        MapperUpdate<TariffCreateEditDto, Tariff> {

    @Override
    public Tariff map(TariffCreateEditDto tariffCreateEditDto) {
        Tariff tariff = new Tariff();
        tariff.setPrice(tariffCreateEditDto.getPrice());
        tariff.setType(tariffCreateEditDto.getType());
        return tariff;
    }

    @Override
    public Tariff map(TariffCreateEditDto editDto, Tariff toObject) {
        toObject.setType(editDto.getType());
        toObject.setPrice(editDto.getPrice());
        return toObject;
    }
}

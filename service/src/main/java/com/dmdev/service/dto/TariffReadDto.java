package com.dmdev.service.dto;

import com.dmdev.service.entity.TariffType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class TariffReadDto {

    Integer id;
    TariffType type;
    BigDecimal price;

}

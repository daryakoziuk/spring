package com.dmdev.service.dto;

import com.dmdev.service.contoller.validation.EnumName;
import com.dmdev.service.entity.TariffType;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder
public class TariffCreateEditDto {

    @EnumName(regexp = "(DAYTIME|HOURLY)")
    TariffType type;

    @NotNull(message = "Price can't be blank")
    BigDecimal price;
}

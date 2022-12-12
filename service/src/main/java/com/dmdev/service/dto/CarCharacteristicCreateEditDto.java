package com.dmdev.service.dto;

import com.dmdev.service.contoller.validation.EnumName;
import com.dmdev.service.entity.TypeFuel;
import com.dmdev.service.entity.TypeTransmission;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Value
public class CarCharacteristicCreateEditDto {

    @NotBlank
    Integer engineVolume;

    Long carId;

    @EnumName(regexp = "(AUTOMATIC|MANUAL)")
    @NotBlank
    TypeTransmission transmission;

    @EnumName(regexp = "( PETROL|DIESEL|ELECTRIC)")
    @NotBlank
    TypeFuel type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank
    LocalDate dateRelease;
}

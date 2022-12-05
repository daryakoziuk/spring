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

    @NotBlank(message = "Engine volume can't be blank")
    Integer engineVolume;

    Long carId;

    @EnumName(regexp = "(AUTOMATIC|MANUAL)")
    @NotBlank(message = "Transmission fuel can't be blank")
    TypeTransmission transmission;

    @EnumName(regexp = "( PETROL|DIESEL|ELECTRIC)")
    @NotBlank(message = "Type fuel can't be blank")
    TypeFuel type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank(message = "Date release can't be blank")
    LocalDate dateRelease;
}

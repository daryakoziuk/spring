package com.dmdev.service.dto;

import com.dmdev.service.contoller.validation.EnumName;
import com.dmdev.service.entity.Status;
import com.dmdev.service.entity.TypeFuel;
import com.dmdev.service.entity.TypeTransmission;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class CarCreateEditDto {

    CarCharacteristicCreateEditDto carCharacteristic;

    @NotBlank(message = "Model can't be blank")
    String model;

    @EnumName(regexp = "(FREE|USED|REPAIRED)")
    Status status;

    MultipartFile image;

    @NotNull(message = "Engine volume can't be null")
    Integer engineVolume;

    @EnumName(regexp = "(AUTOMATIC|MANUAL)")
    TypeTransmission transmission;

    @EnumName(regexp = "(PETROL|DIESEL|ELECTRIC)")
    TypeFuel type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateRelease;

}

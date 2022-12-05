package com.dmdev.service.dto;

import com.dmdev.service.entity.TypeFuel;
import com.dmdev.service.entity.TypeTransmission;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FilterCar {

    private List<String>model;
    private Integer engineVolume;
    private List<TypeTransmission> transmission;
    private List<TypeFuel> type;
    private LocalDate dateRelease;
}

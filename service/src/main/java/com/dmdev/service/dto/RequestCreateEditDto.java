package com.dmdev.service.dto;

import com.dmdev.service.contoller.validation.EnumName;
import com.dmdev.service.entity.RequestStatus;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
public class RequestCreateEditDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime dateRequest;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime dateReturn;

    @NotNull
    Integer tariffId;

    @NotNull
    Long userId;

    @NotNull
    Long carId;

    @EnumName(regexp = "(OPEN|CLOSE)")
    RequestStatus status;
}

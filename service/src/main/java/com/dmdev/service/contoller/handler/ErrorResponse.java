package com.dmdev.service.contoller.handler;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class ErrorResponse {

    List<String> message;
    HttpStatus httpStatus;
    LocalDateTime dateTime;
}

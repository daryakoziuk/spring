package com.dmdev.service.contoller.rest;

import com.dmdev.service.dto.CarReadDto;
import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class RestCarController {

    private final CarService carService;

    @GetMapping
    public Page<CarReadDto> findAll(FilterCar filter, Pageable pageable) {
        return carService.findAll(filter, pageable);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable("id") Long id) {
        return carService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }
}

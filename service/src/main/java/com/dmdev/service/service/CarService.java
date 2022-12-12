package com.dmdev.service.service;

import com.dmdev.service.dao.CarRepository;
import com.dmdev.service.dto.CarCreateEditDto;
import com.dmdev.service.dto.CarReadDto;
import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.dto.predicate.CarPredicateBuilder;
import com.dmdev.service.entity.Car;
import com.dmdev.service.mapper.CarCreateEditMapper;
import com.dmdev.service.mapper.Mapper;
import com.querydsl.core.types.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Getter
public class CarService implements CrudService<CarReadDto, Car, Long> {

    private final CarPredicateBuilder carPredicateBuilder;
    private final CarRepository repository;
    private final Mapper<Car, CarReadDto> mapper;
    private final CarCreateEditMapper carCreateEditMapper;
    private final ImageService imageService;

    public Page<CarReadDto> findAll(FilterCar filter, Pageable pageable) {
        Predicate predicate = carPredicateBuilder.builder(filter);
        return repository.findAll(predicate, pageable)
                .map(mapper::map);
    }

    @Transactional
    public Optional<CarReadDto> update(Long id, CarCreateEditDto car) {
        return repository.findById(id)
                .map(entity -> {
                    if (car.getImage() != null) {
                        uploadImage(car.getImage());
                    }
                    return carCreateEditMapper.map(car, entity);
                })
                .map(repository::saveAndFlush)
                .map(mapper::map);
    }

    @Transactional
    public CarReadDto create(CarCreateEditDto carCreateEditDto) {
        return Optional.of(carCreateEditDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return carCreateEditMapper.map(dto);
                })
                .map(repository::save)
                .map(mapper::map).orElseThrow();
    }

    public Optional<byte[]> findImage(Long id) {
        return repository.findById(id)
                .map(Car::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}

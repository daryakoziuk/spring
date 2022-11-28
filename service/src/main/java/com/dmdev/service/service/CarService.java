package com.dmdev.service.service;

import com.dmdev.service.dao.CarRepository;
import com.dmdev.service.dto.CarCreateEditDto;
import com.dmdev.service.dto.CarReadDto;
import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.dto.predicate.CarPredicateBuilder;
import com.dmdev.service.entity.Car;
import com.dmdev.service.mapper.CarCreateEditMapper;
import com.dmdev.service.mapper.CarReadMapper;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarService {

    private final CarPredicateBuilder carPredicateBuilder;
    private final CarRepository carRepository;
    private final CarReadMapper carReadMapper;
    private final CarCreateEditMapper carCreateEditMapper;
    private final ImageService imageService;

    public Page<CarReadDto> findAll(FilterCar filter, Pageable pageable) {
        Predicate predicate = carPredicateBuilder.builder(filter);
        return carRepository.findAll(predicate, pageable)
                .map(carReadMapper::map);
    }

    public List<CarReadDto> findAll() {
        return carRepository.findAll()
                .stream().map(carReadMapper::map)
                .toList();
    }

    public Optional<CarReadDto> findById(Long id) {
        return carRepository.findById(id)
                .map(carReadMapper::map);
    }

    @Transactional
    public Optional<CarReadDto> update(Long id, CarCreateEditDto car) {
        return carRepository.findById(id)
                .map(entity -> {
                    uploadImage(car.getImage());
                    return carCreateEditMapper.map(car, entity);
                })
                .map(carRepository::saveAndFlush)
                .map(carReadMapper::map);
    }

    @Transactional
    public CarReadDto create(CarCreateEditDto carCreateEditDto) {
        return Optional.of(carCreateEditDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return carCreateEditMapper.map(dto);
                })
                .map(carRepository::save)
                .map(carReadMapper::map).orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return carRepository.findById(id)
                .map(car -> {
                    carRepository.delete(car);
                    carRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Optional<byte[]> findImage(Long id) {
        return carRepository.findById(id)
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

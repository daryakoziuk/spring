package com.dmdev.service.service;

import com.dmdev.service.dao.CarRepository;
import com.dmdev.service.dao.RequestRepository;
import com.dmdev.service.dto.RequestCreateEditDto;
import com.dmdev.service.dto.RequestReadDto;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.RequestStatus;
import com.dmdev.service.entity.Status;
import com.dmdev.service.entity.TariffType;
import com.dmdev.service.mapper.Mapper;
import com.dmdev.service.mapper.RequestCreateEditMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Getter
public class RequestService implements CrudService<RequestReadDto, Request, Long> {

    private final RequestRepository repository;
    private final CarRepository carRepository;
    private final Mapper<Request, RequestReadDto> mapper;
    private final RequestCreateEditMapper requestCreateEditMapper;

    @Transactional
    public RequestReadDto create(RequestCreateEditDto createEditDto) {
        Optional<Car> optionalCar = carRepository.findById(createEditDto.getCarId());
        if (optionalCar.get().getStatus().equals(Status.USED)){
            return null;
        }
        optionalCar.ifPresent(car -> {
            car.setStatus(Status.USED);
            carRepository.saveAndFlush(car);
        });
        return Optional.of(createEditDto)
                .map(requestCreateEditMapper::map)
                .map(request -> {
                    request.setStatus(RequestStatus.OPEN);
                    return repository.save(request);
                })
                .map(mapper::map)
                .orElseThrow();
    }

    @Transactional
    public void close(Long id, RequestCreateEditDto createEditDto) {
        carRepository.findById(createEditDto.getCarId())
                .map(car -> {
                    car.setStatus(Status.FREE);
                    return carRepository.saveAndFlush(car);
                });
        Optional<Request> optionalRequest = repository.findById(id);
        optionalRequest.ifPresent(request -> {
            requestCreateEditMapper.map(createEditDto, request);
            request.setStatus(RequestStatus.CLOSE);
            repository.saveAndFlush(request);
            mapper.map(request);
        });
    }

    @Transactional
    public Optional<RequestReadDto> update(Long id, RequestCreateEditDto createEditDto) {
        return repository.findById(id)
                .map(request -> requestCreateEditMapper.map(createEditDto, request))
                .map(repository::saveAndFlush)
                .map(mapper::map);
    }

    public Optional<BigDecimal> calculate(Long id) {
        return repository.findById(id)
                .map(request -> {
                    if (request.getTariff().getType().equals(TariffType.DAYTIME)) {
                        return request.getTariff().getPrice()
                                .multiply(new BigDecimal(ChronoUnit.DAYS
                                        .between(request.getDateRequest(), request.getDateReturn())));
                    } else {
                        return request.getTariff().getPrice()
                                .multiply(new BigDecimal(ChronoUnit.HOURS
                                        .between(request.getDateRequest(), request.getDateReturn())));
                    }
                });
    }
}

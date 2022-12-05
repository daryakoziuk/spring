package com.dmdev.service.mapper;

import com.dmdev.service.dao.CarRepository;
import com.dmdev.service.dao.TariffRepository;
import com.dmdev.service.dao.UserRepository;
import com.dmdev.service.dto.RequestCreateEditDto;
import com.dmdev.service.entity.Car;
import com.dmdev.service.entity.Request;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RequestCreateEditMapper implements Mapper<RequestCreateEditDto, Request>,
        MapperUpdate<RequestCreateEditDto, Request> {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final TariffRepository tariffRepository;


    @Override
    public Request map(RequestCreateEditDto requestCreateEditDto) {
        Request request = new Request();
        getRequest(requestCreateEditDto, request);
        return request;
    }

    @Override
    public Request map(RequestCreateEditDto editDto, Request toObject) {
        getRequest(editDto, toObject);
        return toObject;
    }

    private void getRequest(RequestCreateEditDto requestCreateEditDto, Request request) {
        request.setDateRequest(requestCreateEditDto.getDateRequest());
        request.setDateReturn(requestCreateEditDto.getDateReturn());
        request.setCar(getCar(requestCreateEditDto.getCarId()));
        request.setTariff(getTariff(requestCreateEditDto.getTariffId()));
        request.setUser(getUser(requestCreateEditDto.getUserId()));
        request.setStatus(requestCreateEditDto.getStatus());
    }

    private Car getCar(Long id) {
        return Optional.ofNullable(id)
                .flatMap(carRepository::findById)
                .orElse(null);
    }

    private Tariff getTariff(Integer id) {
        return Optional.ofNullable(id)
                .flatMap(tariffRepository::findById)
                .orElse(null);
    }

    private User getUser(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}

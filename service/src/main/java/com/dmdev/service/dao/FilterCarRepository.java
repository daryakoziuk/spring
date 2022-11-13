package com.dmdev.service.dao;

import com.dmdev.service.dto.FilterCar;
import com.dmdev.service.entity.Car;

import java.util.List;

public interface FilterCarRepository {

    List<Car> findAllByFilter(FilterCar filterCar);
}

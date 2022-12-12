package com.dmdev.service.service;

import com.dmdev.service.dao.TariffRepository;
import com.dmdev.service.dto.TariffCreateEditDto;
import com.dmdev.service.dto.TariffReadDto;
import com.dmdev.service.entity.Tariff;
import com.dmdev.service.mapper.Mapper;
import com.dmdev.service.mapper.TariffCreateEditMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class TariffService implements CrudService<TariffReadDto, Tariff, Integer> {

    private final TariffRepository repository;
    private final Mapper<Tariff, TariffReadDto> mapper;
    private final TariffCreateEditMapper tariffCreateEditMapper;

    @Transactional
    public Optional<TariffReadDto> update(Integer id, TariffCreateEditDto createEditDto){
        return repository.findById(id)
                .map(tariff -> tariffCreateEditMapper.map(createEditDto, tariff))
                .map(repository::saveAndFlush)
                .map(mapper::map);
    }

    @Transactional
    public TariffReadDto create(TariffCreateEditDto createEditDto){
        return Optional.of(createEditDto)
                .map(tariffCreateEditMapper::map)
                .map(repository::save)
                .map(mapper::map)
                .orElseThrow();
    }
}

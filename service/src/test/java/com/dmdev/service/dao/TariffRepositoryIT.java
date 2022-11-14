package com.dmdev.service.dao;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.entity.Tariff;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class TariffRepositoryIT extends IntegrationTestBase {

    private final TariffRepository tariffRepository;

    @Test
    void checkFindTariffById() {
        Optional<Tariff> mayBeTariff = tariffRepository.findById(1);

        assertThat(mayBeTariff).isPresent();
    }

    @Test
    void checkFindAll() {
        List<Tariff> tariffs = tariffRepository.findAll();

        assertThat(tariffs).hasSize(2);
    }
}


package com.dmdev.service.dao;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.annotation.IT;
import com.dmdev.service.entity.Tariff;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
@RequiredArgsConstructor
public class TariffRepositoryIT extends IntegrationTestBase {

    private final TariffRepository tariffRepository;

    @Test
    void checkSaveTariff() {
        Tariff tariff = TestDatabaseImporter.getTariff();

        Tariff saveTariff = tariffRepository.save(tariff);

        assertThat(saveTariff.getId()).isNotNull();
    }

    @Test
    void checkUpdateTariff() {
        Optional<Tariff> optionalTariff = tariffRepository.findById(1);
        optionalTariff.get().setPrice(new BigDecimal(20.0));

        tariffRepository.updateTariff(1, optionalTariff.get().getPrice());
        Optional<Tariff> actualTariff = tariffRepository.findById(1);

        assertThat(optionalTariff).isPresent();
        assertEquals(optionalTariff.get().getPrice(), actualTariff.get().getPrice());
    }

    @Test
    void checkDeleteTariff() {
        Optional<Tariff> tariff = tariffRepository.findById(2);

        tariffRepository.delete(tariff.get());
        Optional<Tariff> actual = tariffRepository.findById(2);

        assertThat(actual).isEmpty();
    }

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


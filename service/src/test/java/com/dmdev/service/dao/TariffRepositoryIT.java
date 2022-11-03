package com.dmdev.service.dao;

import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.entity.Tariff;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class TariffRepositoryIT {

    private final TariffRepository tariffRepository;

    @Transactional
    @Test
    void checkSaveTariff() {
        Tariff tariff = TestDatabaseImporter.getTariff();

        Tariff saveTariff = tariffRepository.save(tariff);

        assertThat(saveTariff.getId()).isNotNull();
    }

    @Transactional
    @Test
    void checkUpdateTariff() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        tariff.setPrice(new BigDecimal(15));

        tariffRepository.update(tariff);
        Optional<Tariff> actualTariff = tariffRepository.findById(tariff.getId());

        assertEquals(tariff.getPrice(), actualTariff.get().getPrice());
    }

    @Transactional
    @Test
    void checkDeleteTariff() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);

        tariffRepository.delete(tariff);
        Optional<Tariff> actual = tariffRepository.findById(tariff.getId());

        assertThat(actual).isEmpty();
    }

    @Transactional
    @Test
    void checkFindTariffById() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);

        Optional<Tariff> mayBeTariff = tariffRepository.findById(tariff.getId());

        assertThat(mayBeTariff).isPresent();
        assertEquals(tariff.getId(), mayBeTariff.get().getId());
    }

    @Transactional
    @Test
    void checkFindAll() {
        Tariff tariff = TestDatabaseImporter.getTariff();
        tariffRepository.save(tariff);
        List<Tariff> tariffs = tariffRepository.findAll();

        assertThat(tariffs).hasSize(1);
    }
}


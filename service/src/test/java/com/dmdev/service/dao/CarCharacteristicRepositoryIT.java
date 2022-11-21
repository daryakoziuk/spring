package com.dmdev.service.dao;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.entity.CarCharacteristic;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CarCharacteristicRepositoryIT extends IntegrationTestBase {

    private final CarCharacteristicRepository carCharacteristicRepository;

    @Test
    void checkFindCarCharacteristicById() {
        Optional<CarCharacteristic> mayBeCharacteristic = carCharacteristicRepository.findById(1L);

        assertThat(mayBeCharacteristic).isPresent();
        assertThat(mayBeCharacteristic.get().getId()).isNotNull();
    }

    @Test
    void checkFindAll() {
        List<CarCharacteristic> characteristics = carCharacteristicRepository.findAll();

        assertThat(characteristics).hasSize(4);
    }
}

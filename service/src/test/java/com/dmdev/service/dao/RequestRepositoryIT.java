package com.dmdev.service.dao;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.entity.Request;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class RequestRepositoryIT extends IntegrationTestBase {

    private final RequestRepository requestRepository;

    @Test
    void checkFindRequestById() {
        Optional<Request> mayBeRequest = requestRepository.findById(2L);

        assertThat(mayBeRequest).isPresent();
    }

    @Test
    void checkFindAll() {
        List<Request> requests = requestRepository.findAll();

        assertThat(requests).hasSize(5);
    }
}

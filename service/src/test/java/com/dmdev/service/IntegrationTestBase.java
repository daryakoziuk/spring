package com.dmdev.service;

import com.dmdev.service.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@Sql("classpath:dml.sql")
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.1");

    @BeforeAll
    static void runContainer() {
        postgres.start();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }
}

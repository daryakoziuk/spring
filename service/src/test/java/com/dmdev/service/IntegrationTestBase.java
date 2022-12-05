package com.dmdev.service;

import com.dmdev.service.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@Sql("classpath:dml.sql")
@WithMockUser(username = "test11@gmail.com", password = "testtesttest", authorities = {"USER", "ADMIN"})
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

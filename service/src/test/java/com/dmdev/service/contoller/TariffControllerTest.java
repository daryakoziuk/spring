package com.dmdev.service.contoller;

import com.dmdev.service.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class TariffControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private static final Integer TARIFF_ID = 1;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/tariffs"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("tariff/tariffs"));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/tariffs/{id}", TARIFF_ID))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("tariff/tariff"),
                        model().attributeExists("tariff", "types"));
    }

    @Test
    void checkCreatePage() throws Exception {
        mockMvc.perform(get("/tariffs/create"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("tariff/create"),
                        model().attributeExists("tariff", "types"));
    }

    @Test
    void checkCreateTariff() throws Exception {
        mockMvc.perform(post("/tariffs")
                        .param("type", "DAYTIME")
                        .param("price", "30.00")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/tariffs/{\\d}"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/tariffs/{id}/update", TARIFF_ID)
                        .param("type", "DAYTIME")
                        .param("price", "50.00")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/tariffs/{\\id}"));
    }

    @Test
    void checkDeleteWhenTariffExists() throws Exception {
        mockMvc.perform(post("/tariffs/{id}/delete", TARIFF_ID))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/tariffs"));
    }

    @Test
    void checkDeleteIfTariffDoesntExist() throws Exception {
        mockMvc.perform(post("/tariffs/{id}/delete", 6))
                .andExpect(status().is4xxClientError());
    }
}
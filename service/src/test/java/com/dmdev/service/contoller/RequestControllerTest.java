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
class RequestControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private static final Long REQUEST_ID = 1L;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/requests"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("request/requests"),
                        model().attributeExists("requests")
                );
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/requests/{id}", REQUEST_ID))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("request/request"),
                        model().attributeExists("request", "statuses", "users", "cars", "tariffs", "user"));
    }

    @Test
    void checkPageCreate() throws Exception {
        mockMvc.perform(get("/requests/create"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("request/create"),
                        model().attributeExists("request", "statuses", "users", "cars", "tariffs"));
    }

    @Test
    void checkCreateRequest() throws Exception {
        mockMvc.perform(post("/requests")
                        .param("dateRequest", "2022-12-02T00:00")
                        .param("dateReturn", "2022-12-03T00:00")
                        .param("tariffId", "1")
                        .param("userId", "2")
                        .param("carId", "2")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/requests/{\\d}"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/requests/{id}/update", REQUEST_ID)
                        .param("action", "update")
                        .param("dateRequest", "2022-12-02T00:00")
                        .param("dateReturn", "2022-12-03T00:00")
                        .param("tariffId", "1")
                        .param("userId", "2")
                        .param("carId", "2"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/requests/{\\d}"));
    }

    @Test
    void close() throws Exception {
        mockMvc.perform(post("/requests/{id}/update", REQUEST_ID)
                        .param("action", "close")
                        .param("action", "update")
                        .param("dateRequest", "2022-12-02T00:00")
                        .param("dateReturn", "2022-12-03T00:00")
                        .param("tariffId", "1")
                        .param("userId", "2")
                        .param("carId", "2"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/requests/{\\d}"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/requests/{id}/delete", REQUEST_ID))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/requests"));
    }

    @Test
    void calculate() throws Exception {
        mockMvc.perform(get("/requests/{id}/calculate", REQUEST_ID))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("request/calculate"),
                        model().attributeExists("request", "amount"));
    }
}
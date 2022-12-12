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
class CarControllerTest extends IntegrationTestBase {

    private static final Long CAR_ID = 1L;
    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("car/cars"),
                        model().attributeExists("cars", "transmissions", "types", "filter"));
    }

    @Test
    void checkFindByIdIfCarExists() throws Exception {
        mockMvc.perform(get("/cars/{id}", CAR_ID))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("car/car"),
                        model().attributeExists("car", "transmissions", "types", "statuses"));
    }

    @Test
    void checkFindByIdWhenCarDoesntExist() throws Exception {
        mockMvc.perform(get("/cars/{id}", 6L))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/cars/{id}/update", CAR_ID)
                        .param("model", "ALFA ROMEO")
                        .param("status", "FREE")
                        .param("engineVolume", "1900")
                        .param("transmission", "MANUAL")
                        .param("dateRelease", "2001-01-01")
                        .param("type", "PETROL")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/cars/{\\id}")
                );
    }

    @Test
    void checkUpdateWhenHaveMistake() throws Exception {
        mockMvc.perform(post("/cars/{id}/update", 6L))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void checkPageCreate() throws Exception {
        mockMvc.perform(get("/cars/create"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("car/create"),
                        model().attributeExists("car", "statuses", "transmissions", "types")
                );
    }

    @Test
    void checkCreateCarIfAllRight() throws Exception {
        mockMvc.perform(post("/cars")
                        .param("model", "FORD")
                        .param("status", "FREE")
                        .param("engineVolume", "1900")
                        .param("image", "image")
                        .param("transmission", "MANUAL")
                        .param("dateRelease", "2001-01-01")
                        .param("typeFuel", "PETROL")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/cars/{id}"));
    }

    @Test
    void checkDeleteWhenCarExists() throws Exception {
        mockMvc.perform(post("/cars/{id}/delete", CAR_ID))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/cars")
                );
    }

    @Test
    void checkDeleteWhenCarDoesntExist() throws Exception {
        mockMvc.perform(post("/cars/{id}/delete", 6L))
                .andExpect(status().is4xxClientError());
    }
}
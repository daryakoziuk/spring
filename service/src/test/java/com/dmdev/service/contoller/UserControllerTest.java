package com.dmdev.service.contoller;

import com.dmdev.service.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private static final Long USER_ID = 1L;
    private final MockMvc mockMvc;

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/users/registration"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/registration"))
                .andExpect(model().attributeExists("user", "roles"));
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attribute("users", hasSize(5)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/{id}", USER_ID))
                .andExpectAll(
                        view().name("user/user"),
                        status().is2xxSuccessful(),
                        model().attributeExists("user", "roles"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param("username", "test")
                        .param("firstname", "Test")
                        .param("lastname", "TestTest")
                        .param("password", "testtest")
                        .param("role", "ADMIN"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/{id}/update", USER_ID)
                        .param("firstname", "Irina22")
                        .param("lastname", "Popova")
                        .param("username", "irina@gmail.com")
                        .param("role", "ADMIN"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/{id}/delete", USER_ID))
                .andExpectAll(
                        redirectedUrl("/users"),
                        status().is3xxRedirection());
    }
}
package com.dmdev.service.contoller;

import com.dmdev.service.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class LoginControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void loginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        view().name("user/login"));
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "szeta@gmail.com")
                        .param("password", "3333"))
                .andExpectAll(
                        redirectedUrl("/users"),
                        status().is3xxRedirection());
    }
}
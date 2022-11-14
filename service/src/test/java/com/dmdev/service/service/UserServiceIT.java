package com.dmdev.service.service;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.dto.UserCreateDto;
import com.dmdev.service.dto.UserEditDto;
import com.dmdev.service.dto.UserReadDto;
import com.dmdev.service.entity.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_ID = 1L;
    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();

        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        Optional<UserReadDto> optionalUser = userService.findById(USER_ID);

        assertThat(optionalUser).isPresent();
        assertEquals(USER_ID, optionalUser.get().getId());
    }

    @Test
    void create() {
        UserCreateDto userCreateDto = new UserCreateDto(
                "test@mail.ru",
                "test",
                "test",
                "test",
                Role.USER
        );

        UserReadDto actualUser = userService.create(userCreateDto);

        assertEquals(userCreateDto.getLastname(), actualUser.getLastname());
        assertEquals(userCreateDto.getFirstname(), actualUser.getFirstname());
        assertEquals(userCreateDto.getUsername(), actualUser.getUsername());
        assertSame(userCreateDto.getRole(), actualUser.getRole());
    }

    @Test
    void update() {
        UserEditDto userDto = new UserEditDto(
                "test@mail.ru",
                "test",
                "test",
                Role.USER
        );
        Optional<UserReadDto> actual = userService.update(USER_ID, userDto);

        assertThat(actual).isPresent();
        actual.ifPresent(actualUser ->
        {
            assertEquals(userDto.getLastname(), actualUser.getLastname());
            assertEquals(userDto.getFirstname(), actualUser.getFirstname());
            assertEquals(userDto.getUsername(), actualUser.getUsername());
            assertSame(userDto.getRole(), actualUser.getRole());
        });
    }

    @Test
    void delete() {
        boolean result = userService.delete(USER_ID);

        assertTrue(result);
    }
}
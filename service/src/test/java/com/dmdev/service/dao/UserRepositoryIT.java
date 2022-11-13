package com.dmdev.service.dao;

import com.dmdev.service.IntegrationTestBase;
import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.annotation.IT;
import com.dmdev.service.dto.FilterUser;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class UserRepositoryIT extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkSaveUser() {
        User user = TestDatabaseImporter.getUser();

        User saveUser = userRepository.save(user);

        assertThat(saveUser.getId()).isNotNull();
    }

    @Test
    void checkDeleteUser() {
        Optional<User> userOptional = userRepository.findById(1L);

        userRepository.delete(userOptional.get());
        Optional<User> optionalUser = userRepository.findById(1L);

        assertThat(optionalUser).isEmpty();
    }

    @Test
    void checkUpdateUser() {
        Optional<User> userOptional = userRepository.findById(2L);
        userOptional.get().setUsername("ivan@gmail.com");

        userRepository.updateUsername(2L, userOptional.get().getUsername());
        Optional<User> optionalUser = userRepository.findById(2L);

        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().getUsername()).isEqualTo("ivan@gmail.com");
    }

    @Test
    void checkFindUserById() {
        Optional<User> mayBeUser = userRepository.findById(1L);

        assertThat(mayBeUser).isPresent();
    }

    @Test
    void checkUserByUsername() {
        Optional<User> mayBeUser = userRepository.findUserByUsername("irina@gmail.com");

        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser.get().getUsername()).isEqualTo("irina@gmail.com");
    }

    @Test
    void checkUserByFilter() {
        FilterUser filterUser = FilterUser.builder()
                .firstname("Olga")
                .build();

        List<User> users = userRepository.findAllByFilter(filterUser);

        assertThat(users).hasSize(1);
    }

    @Test
    void checkFindAll() {
        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(5);
    }
}
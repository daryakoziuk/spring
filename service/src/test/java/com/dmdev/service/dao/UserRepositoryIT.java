package com.dmdev.service.dao;

import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.dto.FilterUser;
import com.dmdev.service.entity.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class UserRepositoryIT {

    private final UserRepository userRepository;

    @Transactional
    @Test
    void checkSaveUser() {
        User user = TestDatabaseImporter.getUser();

        User saveUser = userRepository.save(user);

        assertThat(saveUser.getId()).isNotNull();
    }

    @Transactional
    @Test
    void checkDeleteUser() {
        User user = TestDatabaseImporter.getUser();
        User newUser = userRepository.save(user);

        userRepository.delete(newUser);
        Optional<User> optionalUser = userRepository.findById(newUser.getId());

        assertThat(optionalUser).isEmpty();
    }

    @Transactional
    @Test
    void checkUpdateUser() {
        User user = TestDatabaseImporter.getUser();
        User newUser = userRepository.save(user);
        newUser.setUsername("ivan@gmail.com");

        userRepository.update(newUser);
        Optional<User> optionalUser = userRepository.findById(newUser.getId());

        assertThat(newUser.getUsername()).isEqualTo("ivan@gmail.com");
    }

    @Transactional
    @Test
    void checkFindUserById() {
        User user = TestDatabaseImporter.getUser();
        User newUser = userRepository.save(user);

        Optional<User> mayBeUser = userRepository.findById(newUser.getId());

        assertThat(mayBeUser.get().getId()).isNotNull();
    }

    @Transactional
    @Test
    void checkUserByUsername() {
        User user = TestDatabaseImporter.getUser();
        userRepository.save(user);

        Optional<User> mayBeUser = userRepository.findUserByUsername("olya22@gmail.com");

        assertThat(mayBeUser.get().getId()).isNotNull();
    }

    @Transactional
    @Test
    void checkUserByFilter() {
        User user = TestDatabaseImporter.getUser();
        userRepository.save(user);
        FilterUser filterUser = FilterUser.builder()
                .firstname("Olya")
                .build();

        List<User> users = userRepository.findUserByFilter(filterUser);

        assertThat(users).hasSize(1);
    }

    @Transactional
    @Test
    void checkFindAll() {
        User user = TestDatabaseImporter.getUser();
        userRepository.save(user);
        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(1);
    }
}
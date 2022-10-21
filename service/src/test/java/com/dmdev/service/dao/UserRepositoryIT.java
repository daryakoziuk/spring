package com.dmdev.service.dao;

import com.dmdev.service.TestDatabaseImporter;
import com.dmdev.service.dto.FilterUser;
import com.dmdev.service.entity.User;
import com.dmdev.service.TestBeanImporter;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIT {

    private final UserRepository userRepository = TestBeanImporter.getUserRepository();

    @BeforeAll
    static void initDb() {
        TestDatabaseImporter.insertDatabase(TestBeanImporter.getSessionFactory());
    }

    @AfterAll
    static void close() {
        TestBeanImporter.getSessionFactory().close();
    }

    @Test
    void checkSaveUser() {
        Session session = TestBeanImporter.getSession();
        session.beginTransaction();
        User user = TestDatabaseImporter.getUser();

        User saveUser = userRepository.save(user);

        assertThat(saveUser.getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkDeleteUser() {
        Session session = TestBeanImporter.getSession();
        session.beginTransaction();
        User user = TestDatabaseImporter.getUser();
        User newUser = userRepository.save(user);
        session.flush();

        userRepository.delete(newUser);
        User actual = session.find(User.class, newUser.getId());

        assertThat(actual).isNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUpdateUser() {
        Session session = TestBeanImporter.getSession();
        session.beginTransaction();
        User user = TestDatabaseImporter.getUser();
        User newUser = userRepository.save(user);
        newUser.setUsername("ivan@gmail.com");

        userRepository.update(newUser);
        session.flush();
        session.clear();
        User actual = session.find(User.class, user.getId());

        assertThat(actual.getUsername()).isEqualTo("ivan@gmail.com");
        session.getTransaction().rollback();
    }

    @Test
    void checkFindUserById() {
        Session session = TestBeanImporter.getSession();
        session.beginTransaction();
        User user = TestDatabaseImporter.getUser();
        User newUser = userRepository.save(user);

        Optional<User> mayBeUser = userRepository.findById(newUser.getId());

        assertThat(mayBeUser.get().getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUserByUsername() {
        Session session = TestBeanImporter.getSession();
        session.beginTransaction();

        Optional<User> mayBeUser = userRepository.findUserByUsername("olya@gmail.com");

        assertThat(mayBeUser.get().getId()).isNotNull();
        session.getTransaction().rollback();
    }

    @Test
    void checkUserByFilter() {
        Session session = TestBeanImporter.getSession();
        session.beginTransaction();
        FilterUser filterUser = FilterUser.builder()
                .firstname("Olya")
                .build();

        List<User> users = userRepository.findUserByFilter(filterUser);

        assertThat(users).hasSize(1);
        session.getTransaction().rollback();
    }

    @Test
    void checkFindAll() {
        Session session = TestBeanImporter.getSession();
        session.beginTransaction();

        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2);
        session.getTransaction().rollback();
    }
}
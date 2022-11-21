package com.dmdev.service.dao;

import com.dmdev.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, FilterUserRepository {

    @Query("select u from User u where u.username =:username")
    Optional<User> findUserByUsername(String username);
}

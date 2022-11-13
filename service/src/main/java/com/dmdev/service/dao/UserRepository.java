package com.dmdev.service.dao;

import com.dmdev.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, FilterUserRepository {

    @Query("select u from User u where u.username=:username")
    Optional<User> findUserByUsername(String username);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update User u set u.username=:username where u.id=:id")
    int updateUsername(Long id, String username);
}

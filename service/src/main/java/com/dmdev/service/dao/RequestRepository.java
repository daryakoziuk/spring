package com.dmdev.service.dao;

import com.dmdev.service.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Request r set r.dateReturn=:dateReturn where r.id=:id")
    int updateDateReturn(Long id, LocalDateTime dateReturn);
}

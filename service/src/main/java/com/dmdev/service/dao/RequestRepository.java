package com.dmdev.service.dao;

import com.dmdev.service.entity.Request;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RequestRepository extends BaseRepository<Long, Request> {

    public RequestRepository(EntityManager entityManager) {
        super(entityManager, Request.class);
    }
}

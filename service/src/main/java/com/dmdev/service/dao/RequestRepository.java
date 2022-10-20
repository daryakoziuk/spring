package com.dmdev.service.dao;

import com.dmdev.service.entity.Request;

import javax.persistence.EntityManager;

public class RequestRepository extends BaseRepository<Long, Request> {

    public RequestRepository(EntityManager entityManager) {
        super(entityManager, Request.class);
    }
}

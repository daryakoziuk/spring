package com.dmdev.service.dao;

import com.dmdev.service.dto.FilterUser;
import com.dmdev.service.entity.User;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(FilterUser filter);
}

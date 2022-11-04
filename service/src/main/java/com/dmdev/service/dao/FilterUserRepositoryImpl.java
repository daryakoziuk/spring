package com.dmdev.service.dao;

import com.dmdev.service.dto.FilterUser;
import com.dmdev.service.dto.predicate.UserPredicateBuilder;
import com.dmdev.service.entity.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdev.service.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    private final EntityManager entityManager;
    private final UserPredicateBuilder userPredicateBuilder;

    @Override
    public List<User> findAllByFilter(FilterUser filter) {
        Predicate predicate = userPredicateBuilder.builder(filter);
        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}

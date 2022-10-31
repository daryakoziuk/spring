package com.dmdev.service.dto.predicate;

import com.dmdev.service.dto.FilterUser;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

import static com.dmdev.service.entity.QUser.user;

@Component
public class UserPredicateBuilder implements PredicateBuilder<Predicate, FilterUser> {

    @Override
    public Predicate builder(FilterUser filter) {
        return QPredicate.builder()
                .add(filter.getLastname(), user.personalInfo.lastname::eq)
                .add(filter.getFirstname(), user.personalInfo.firstname::eq)
                .buildAll();
    }
}

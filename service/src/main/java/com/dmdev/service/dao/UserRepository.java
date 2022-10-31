package com.dmdev.service.dao;

import com.dmdev.service.dto.FilterUser;
import com.dmdev.service.dto.predicate.UserPredicateBuilder;
import com.dmdev.service.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.dmdev.service.entity.QUser.user;

@Repository
public class UserRepository extends BaseRepository<Long, User> {

    private final UserPredicateBuilder userPredicate;

    public UserRepository(EntityManager entityManager, UserPredicateBuilder userPredicate) {
        super(entityManager, User.class);
        this.userPredicate = userPredicate;
    }

    public List<User> findUserByFilter(FilterUser filterUser) {
        return new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(userPredicate.builder(filterUser))
                .fetch();
    }

    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(user.username.eq(username))
                .fetchFirst());
    }
}

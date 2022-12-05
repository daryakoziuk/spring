package com.dmdev.service.dto.predicate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PredicateCriteria {

    private final List<Predicate> listPredicates = new ArrayList<>();

    public static PredicateCriteria builder() {
        return new PredicateCriteria();
    }

    public <T> PredicateCriteria add(T object, Function<T, Predicate> function) {
        if (object != null) {
            listPredicates.add(function.apply(object));
        }
        return this;
    }

    public List<Predicate> getPredicates() {
        return listPredicates;
    }
}

package com.dmdev.service.dto.predicate;

public interface PredicateBuilder<P, F> {

    P builder(F filter);
}

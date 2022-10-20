package com.dmdev.service.dto.predicate;

public interface BuilderPredicate <P, F>{

    P builder(F filter);
}

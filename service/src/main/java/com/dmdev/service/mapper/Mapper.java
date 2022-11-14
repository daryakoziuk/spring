package com.dmdev.service.mapper;

public interface Mapper<F, T> {

    default T map(F f) {
        return null;
    }

    default T map(F f, T toObject) {
        return toObject;
    }
}

package com.dmdev.service.mapper;

public interface MapperUpdate<F, T> {

    T map(F editDto, T toObject);

}

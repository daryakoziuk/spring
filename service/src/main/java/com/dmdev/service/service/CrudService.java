package com.dmdev.service.service;

import com.dmdev.service.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CrudService<D, T, I> {

    JpaRepository<T, I> getRepository();

    Mapper<T, D> getMapper();

    default List<D> findAll() {
        return getRepository().findAll()
                .stream().map(getMapper()::map)
                .toList();
    }

    default Optional<D> findById(I id) {
        return getRepository().findById(id)
                .map(getMapper()::map);
    }

    default boolean delete(I id) {
        return getRepository().findById(id)
                .map(entity -> {
                    getRepository().delete(entity);
                    getRepository().flush();
                    return true;
                })
                .orElse(false);
    }
}

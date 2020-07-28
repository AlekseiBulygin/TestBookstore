package com.example.bookstore.mapper;

public interface Mapper <E, D> {
    E toEntity(D dto);

    D toDto(E entity);

}

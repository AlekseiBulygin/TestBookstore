package com.example.bookstore.mapper;

import org.modelmapper.ModelMapper;
import java.util.Objects;

public abstract class AbstractMapper<E, D> implements Mapper<E, D> {
    private final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    AbstractMapper(Class<E> entityClass, Class<D> dtoClass, ModelMapper mapper){
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.modelMapper = mapper;
    }

    @Override
    public E toEntity(D dto) {
        return Objects.isNull(dto)
                ? null
                : modelMapper.map(dto, entityClass);
    }

    @Override
    public D toDto(E entity) {
        return Objects.isNull(entity)
                ? null
                : modelMapper.map(entity, dtoClass);
    }
}

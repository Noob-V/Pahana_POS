package com.pahanaedu.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface EntityMapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);

    default List<D> toDtoList(Collection<E> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    default List<E> toEntityList(Collection<D> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
package com.leadgen.backend.service;


import java.util.List;

public interface GenericService<U> {
    U save(U dto);

    U update(U dto, Long id);

    U getById(Long id);

    List<U> getAll();

    void delete(Long id);
}
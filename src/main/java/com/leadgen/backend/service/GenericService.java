package com.leadgen.backend.service;


import java.util.List;

public interface GenericService<T, DTO> {
    DTO save(DTO dto);

    DTO update(DTO dto, Long id);

    DTO getById(Long id);

    List<DTO> getAll();

    void delete(Long id);
}
package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Utils.MapperUtil;
import com.leadgen.backend.service.GenericService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class GenericServiceImpl<T,U> implements GenericService<U> {

    private final JpaRepository<T, Long> repository;
    private final ModelMapper modelMapper;
    private final Class<T> entityClass;
    private final Class<U> dtoClass;

    public GenericServiceImpl(JpaRepository<T, Long> repository, ModelMapper modelMapper, Class<T> entityClass, Class<U> dtoClass) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public U save(U dto) {
        T entity = modelMapper.map(dto, entityClass);
        entity = repository.save(entity);
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public U update(U dto, Long id) {
        Optional<T> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            T entity = modelMapper.map(dto, entityClass);
            entity = repository.save(entity);
            return modelMapper.map(entity, dtoClass);
        } else {
            // Handle entity not found
            return null;
        }
    }

    @Override
    public U getById(Long id) {
        Optional<T> optionalEntity = repository.findById(id);
        return optionalEntity.map(entity -> modelMapper.map(entity, dtoClass)).orElse(null);
    }

    @Override
    public List<U> getAll() {
        List<T> entities = repository.findAll();
        List<U> dtos = new ArrayList<>();
        for (T entity : entities) {
            dtos.add(modelMapper.map(entity, dtoClass));
        }
        return dtos;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}



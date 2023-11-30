package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Utils.MapperUtil;
import com.leadgen.backend.service.GenericService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenericServiceImpl<T, DTO> extends MapperUtil<T, DTO> implements GenericService<T, DTO> {

    private final CrudRepository<T, Long> repository;
    private final Class<T> entityClass;
    private final Class<DTO> dtoClass;

    public GenericServiceImpl(CrudRepository<T, Long> repository, ModelMapper modelMapper, Class<T> entityClass, Class<DTO> dtoClass) {
        super(modelMapper);
        this.repository = repository;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public DTO save(DTO dto) {
        T entity = toEntity(dto);
        entity = repository.save(entity);
        return toDTO(entity);
    }

    @Override
    public DTO update(DTO dto, Long id) {
        Optional<T> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            T entity = optionalEntity.get();
            toEntity(dto);
            entity = repository.save(entity);
            return toDTO(entity);
        } else {
            // Handle entity not found
            return null;
        }
    }

    @Override
    public DTO getById(Long id) {
        Optional<T> optionalEntity = repository.findById(id);
        return optionalEntity.map(entity -> toDTO(entity)).orElse(null);
    }

    @Override
    public List<DTO> getAll() {
        List<T> entities = (List<T>) repository.findAll();
        return entities.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}


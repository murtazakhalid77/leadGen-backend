package com.leadgen.backend.controller;

import com.leadgen.backend.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<T> {

    private final GenericService<T> genericService;

    public GenericController(GenericService<T> genericService) {
        this.genericService = genericService;
    }

    @PostMapping("save")
    public ResponseEntity<T> create(@RequestBody T dto) {
        T createdDto = genericService.save(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @GetMapping("getBy/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        T dto = genericService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("getAll")
    public ResponseEntity<List<T>> getAll() {
        List<T> dtos = genericService.getAll();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T dto) {
        T updatedDto = genericService.update(dto, id);
        return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genericService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.CategoryDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.model.Category;
import com.leadgen.backend.service.CategoryService;
import com.leadgen.backend.service.TestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category/")
public class CategoryController extends GenericController<CategoryDTO> {

    @Autowired
    CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        super(categoryService);
    }


    @PostMapping("saveCategory")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category createdDto = categoryService.saveCat(category);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

}
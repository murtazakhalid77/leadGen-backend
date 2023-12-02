package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.CategoryDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.service.CategoryService;
import com.leadgen.backend.service.TestServiceInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category/")
public class CategoryController extends GenericController<CategoryDTO> {

    public CategoryController(CategoryService categoryService) {
        super(categoryService);
    }
}
package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.CategoryDTO;
import com.leadgen.backend.model.Category;
import com.leadgen.backend.repository.CategoryRepository;
import com.leadgen.backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, CategoryDTO> implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        super(categoryRepository, modelMapper, Category.class, CategoryDTO.class);
    }

    @Override
    public Category saveCat(Category category) {


        // Save the category along with its associated subcategories
        return categoryRepository.save(category);
    }
}

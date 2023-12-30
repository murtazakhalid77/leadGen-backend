package com.leadgen.backend.service;

import com.leadgen.backend.Dto.CategoryDTO;
import com.leadgen.backend.model.Category;

public interface CategoryService extends GenericService<CategoryDTO>{

    Category saveCat(Category category);
}

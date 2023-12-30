package com.leadgen.backend.service;

import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.model.SubCategory;
import com.leadgen.backend.model.Test;

import java.util.List;

public interface SubCategoryService extends GenericService<SubCategoryDTO>{


    List<SubCategory> getSubCategoryByName(String categoryName);
}

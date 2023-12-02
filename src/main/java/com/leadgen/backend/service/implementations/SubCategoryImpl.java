package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.model.SubCategory;
import com.leadgen.backend.model.Test;
import com.leadgen.backend.repository.SubCategoryRepository;
import com.leadgen.backend.service.SubCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryImpl extends GenericServiceImpl<SubCategory, SubCategoryDTO> implements SubCategoryService {

    @Autowired
    public SubCategoryImpl(SubCategoryRepository subCategoryRepository, ModelMapper modelMapper) {
        super(subCategoryRepository, modelMapper, SubCategory.class, SubCategoryDTO.class);
    }
}

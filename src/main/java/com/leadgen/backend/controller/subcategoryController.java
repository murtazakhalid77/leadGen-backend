package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.BidDTO;
import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.model.SubCategory;
import com.leadgen.backend.model.Test;
import com.leadgen.backend.service.SubCategoryService;
import com.leadgen.backend.service.TestServiceInterface;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/subcategory/")
@Api(value = "SubCategoryController", tags = "SubCategory Controller")
public class subcategoryController extends GenericController<SubCategoryDTO> {

    public subcategoryController(SubCategoryService subCategoryService) {
        super(subCategoryService);
    }
}

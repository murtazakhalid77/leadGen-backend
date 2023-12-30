package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.BidDTO;
import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.model.SubCategory;
import com.leadgen.backend.model.Test;
import com.leadgen.backend.service.SubCategoryService;
import com.leadgen.backend.service.TestServiceInterface;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subcategory/")
@Api(value = "SubCategoryController", tags = "SubCategory Controller")
public class subcategoryController extends GenericController<SubCategoryDTO> {

    @Autowired
    SubCategoryService subCategoryService;

    public subcategoryController(SubCategoryService subCategoryService) {
        super(subCategoryService);
    }


    @GetMapping("getByName/{categoryName}")
    public ResponseEntity<List<SubCategory>> getSubCategoriesByName(@PathVariable String categoryName) {
        List<SubCategory> subCategories = subCategoryService.getSubCategoryByName(categoryName);
        return ResponseEntity.ok(subCategories);
    }



}

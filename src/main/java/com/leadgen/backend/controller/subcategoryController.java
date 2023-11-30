package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.BidDTO;
import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.service.SubCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class subcategoryController {
    private final SubCategoryService subCategoryService;


    public subcategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping("/subcategory")
    public ResponseEntity<SubCategoryDTO> createLocation(@Valid @RequestBody SubCategoryDTO subCategoryDTO) {
        return ResponseEntity.ok(subCategoryService.save(subCategoryDTO));
    }
}

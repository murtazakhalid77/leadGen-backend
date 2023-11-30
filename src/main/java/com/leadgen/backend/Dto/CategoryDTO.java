package com.leadgen.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO  {
    private Long id;

    @NotEmpty(message = "Category name cannot be empty")
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    private String categoryName;

    @Valid
    @NotNull(message = "Subcategory list cannot be null")
    @Size(min = 1, message = "At least one subcategory must be present")
    private List<SubCategoryDTO> subCategoryList;
}

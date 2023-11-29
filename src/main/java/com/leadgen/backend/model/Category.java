package com.leadgen.backend.model;

import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

public class Category {
    private Long Id;
   private String categoryName;

    @OneToMany
    @JoinColumn(name="id")
    private List<SubCategory> subCategoryList; //contimue from here bro.
}

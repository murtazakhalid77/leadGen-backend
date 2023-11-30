package com.leadgen.backend.repository;

import com.leadgen.backend.model.SubCategory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("subCategoryRepositoryQualifier")
public interface SubCategoryRepository extends CrudRepository<SubCategory,Long> {
}

package com.leadgen.backend.repository;

import com.leadgen.backend.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test,Long> {
}

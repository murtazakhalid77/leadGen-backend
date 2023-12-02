package com.leadgen.backend.repository;

import com.leadgen.backend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
}

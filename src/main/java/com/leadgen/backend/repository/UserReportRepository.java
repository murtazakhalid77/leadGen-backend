package com.leadgen.backend.repository;

import com.leadgen.backend.Dto.ReportUserDTO;
import com.leadgen.backend.model.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReportRepository extends JpaRepository<ReportUser, Long> {
}

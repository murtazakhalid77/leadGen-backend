package com.leadgen.backend.repository;

import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.User;
import com.leadgen.backend.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest,Long> {
    List<UserRequest> findUserRequestByApprovedBySystemOrNeedsAdminApproval(Boolean approvedBySystem,Boolean needsAdminApproval);

    List<UserRequest> findUserRequestsByCategory(Category category);
    @Query("SELECT ur FROM UserRequest ur WHERE ur.category = :category AND ur.notifiedNumber < 3 AND ur.notifiable = true AND ur.status = true")
    List<UserRequest> findUserRequestsToNotify(@Param("category") Category category);

    List<UserRequest> findByUserEmailOrderByCreatedDtDesc(String email);

    List<UserRequest> findByCategoryAndApprovedBySystemTrueAndNotifiedNumberGreaterThanOrderByCreatedDtDesc(
            Category category, Long notifiedNumber);






}

package com.leadgen.backend.repository;

import com.leadgen.backend.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest,Long> {
}

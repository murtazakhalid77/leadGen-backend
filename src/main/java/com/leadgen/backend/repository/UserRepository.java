package com.leadgen.backend.repository;

import com.leadgen.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByPhoneNumber(String number);

    Optional<User> findByNationalIdentificationNumber(String nationalIdentificationNumber);

    @Query("SELECT u FROM User u WHERE u.status = :statusValue")
    List<User> findByStatus(@Param("statusValue") Boolean statusValue);



}

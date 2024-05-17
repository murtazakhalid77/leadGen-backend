package com.leadgen.backend.repository;

import com.leadgen.backend.model.UserReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReviewsRepository extends JpaRepository<UserReviews,Long> {

}

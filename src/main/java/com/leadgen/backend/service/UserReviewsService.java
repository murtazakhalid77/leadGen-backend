package com.leadgen.backend.service;

import com.leadgen.backend.Dto.ReviewsDtoUser;
import com.leadgen.backend.Dto.SummaryDto;
import com.leadgen.backend.Dto.UserReviewsDTO;

import java.util.List;

public interface UserReviewsService extends GenericService<UserReviewsDTO> {

    boolean userReview(Long requestId,String emailOfSeller, String rating, String note);

    SummaryDto getUserSummary(String emailOFSeller);

    List<ReviewsDtoUser> getUserReviews(String emailOFSeller);
}

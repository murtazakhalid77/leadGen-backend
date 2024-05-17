package com.leadgen.backend.service;

import com.leadgen.backend.Dto.SummaryDto;
import com.leadgen.backend.Dto.UserReviewsDTO;

public interface UserReviewsService extends GenericService<UserReviewsDTO> {

    boolean userReview(Long requestId,String emailOfSeller, String rating, String note);

    SummaryDto getUserSummary(String emailOFSeller);
}

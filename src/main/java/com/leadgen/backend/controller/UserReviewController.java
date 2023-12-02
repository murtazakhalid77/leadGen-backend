package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserReviewsDTO;
import com.leadgen.backend.service.TestServiceInterface;
import com.leadgen.backend.service.UserReviewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userReviews/")
public class UserReviewController extends GenericController<UserReviewsDTO> {

    public UserReviewController(UserReviewsService userReviewsService) {
        super(userReviewsService);
    }
}

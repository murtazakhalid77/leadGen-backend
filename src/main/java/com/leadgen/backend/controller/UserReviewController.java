package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.ReviewsDtoUser;
import com.leadgen.backend.Dto.SummaryDto;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserReviewsDTO;
import com.leadgen.backend.service.TestServiceInterface;
import com.leadgen.backend.service.UserReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userReviews/")
public class UserReviewController extends GenericController<UserReviewsDTO> {


    @Autowired
    UserReviewsService userReviewsService;
    public UserReviewController(UserReviewsService userReviewsService) {
        super(userReviewsService);
    }
    @PostMapping("rate/{requestId}/{emailOFSeller}/{rating}/{note}")
    public ResponseEntity<?> rate(@PathVariable Long requestId,@PathVariable String emailOFSeller, @PathVariable String rating, @PathVariable String note) {
        try {
            Long rating1  = Long.parseLong(rating);
            boolean userRequest = this.userReviewsService.userReview(requestId,emailOFSeller, rating, note);

            if (userRequest) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);

            }
        } catch (NumberFormatException e) {
            throw  new RuntimeException("some error occured");
        }
    }

    @GetMapping("rate/{emailOFSeller}")
    public ResponseEntity<SummaryDto> rate(@PathVariable String emailOFSeller) {
        try {

            SummaryDto summaryDto = this.userReviewsService.getUserSummary(emailOFSeller);

            if (summaryDto!=null) {
                return ResponseEntity.ok(summaryDto);
            } else {
                return ResponseEntity.ok(null);

            }
        } catch (NumberFormatException e) {
            throw  new RuntimeException("some error occured");
        }
    }

    @GetMapping("reviews/{emailOFSeller}")
    public ResponseEntity<List<ReviewsDtoUser>> getReviews(@PathVariable String emailOFSeller) {
        try {

            List<ReviewsDtoUser> summaryDto = this.userReviewsService.getUserReviews(emailOFSeller);

            if (summaryDto!=null) {
                return ResponseEntity.ok(summaryDto);
            } else {
                return ResponseEntity.ok(null);

            }
        } catch (NumberFormatException e) {
            throw  new RuntimeException("some error occured");
        }
    }
}

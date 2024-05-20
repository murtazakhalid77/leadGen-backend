package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.ReviewsDtoUser;
import com.leadgen.backend.Dto.SummaryDto;
import com.leadgen.backend.Dto.UserReviewsDTO;
import com.leadgen.backend.model.User;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.model.UserReviews;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.repository.UserRequestRepository;
import com.leadgen.backend.repository.UserReviewsRepository;
import com.leadgen.backend.service.UserReviewsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserReviewsServiceImpl extends GenericServiceImpl<UserReviews, UserReviewsDTO> implements UserReviewsService {

    @Autowired
    UserReviewsRepository userReviewsRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRequestRepository userRequestRepository;
   @Autowired
    public UserReviewsServiceImpl(UserReviewsRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, UserReviews.class, UserReviewsDTO.class);
    }

    @Override
    public boolean userReview(Long requestId,String emailOFSeller, String rating, String note) {
        Optional<User> seller = this.userRepository.findByEmail(emailOFSeller);
        Optional<UserRequest> request = this.userRequestRepository.findById(requestId);
        if (seller.isPresent() && request.isPresent()) {
            UserReviews userReview = UserReviews.builder()
                    .rating(Long.parseLong(rating))
                    .user(seller.get())
                    .userRequest(request.get())
                    .note(note).build();
            userReviewsRepository.save(userReview);
            return true;

        } else {
            return false;
        }
    }

    @Override
    public SummaryDto getUserSummary(String emailOFSeller) {
        Optional<User> seller = this.userRepository.findByEmail(emailOFSeller);


        Optional<List<UserRequest>> userRequestList = this.userRequestRepository.findUserRequestByAcceptedSeller(seller.get());


        Double totalEarning = userRequestList.map(requests -> requests.stream()
                .mapToDouble(request -> Double.parseDouble(request.getPrice()))
                .sum()).orElse(0.0);


        double averageRating = seller.map(user -> user.getReviews().stream()
                .mapToDouble(UserReviews::getRating)
                .average()
                .orElse(0.0)).orElse(0.0);


        double overallRating = calculateOverallRating(averageRating);


        return SummaryDto.builder()
                .totalRequestServed(userRequestList.map(List::size).orElse(0))
                .totalEarning(totalEarning)
                .overAllRating(overallRating)
                .build();
    }


    @Override
    public List<ReviewsDtoUser> getUserReviews(String emailOFSeller) {
        Optional<User> seller = this.userRepository.findByEmail(emailOFSeller);
        if (seller.isEmpty()) {
            // Handle case where seller with the provided email is not found
            return Collections.emptyList();
        }

        List<ReviewsDtoUser> reviewsDtoUsers = new ArrayList<>();

        // Iterate through all the reviews associated with the seller
        for (UserReviews review : seller.get().getReviews()) {
            ReviewsDtoUser reviewsDtoUser = new ReviewsDtoUser();

            // Set reviewer name (assuming reviewer name is stored in User entity)
            reviewsDtoUser.setReviewerName(review.getUserRequest().getUser().getFirstName()+" "+review.getUserRequest().getUser().getLastName());

            // Set note from the review
            reviewsDtoUser.setNote(review.getNote());

            // Set rating from the review
            reviewsDtoUser.setRating(review.getRating().doubleValue());

            // Add the constructed ReviewsDtoUser object to the list
            reviewsDtoUsers.add(reviewsDtoUser);
        }

        return reviewsDtoUsers;
    }



    private double calculateOverallRating(double averageRating) {
        // Example custom formula: 80% weight to average rating and 20% weight to some fixed value (e.g., 5.0)
        double weightedAverageRating = 0.8 * averageRating;
        double fixedValueWeight = 0.2 * 5.0; // 20% weight to a fixed value, such as 5.0
        return weightedAverageRating + fixedValueWeight;
    }
}

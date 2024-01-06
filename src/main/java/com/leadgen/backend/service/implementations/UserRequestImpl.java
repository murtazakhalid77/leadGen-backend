package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.RequestDto;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.User;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.repository.CategoryRepository;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.repository.UserRequestRepository;
import com.leadgen.backend.service.UserRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static com.leadgen.backend.helpers.HelperClass.*;

@Service
public class UserRequestImpl extends GenericServiceImpl<UserRequest,UserRequestDTO> implements UserRequestService {

    @Autowired
    UserRequestRepository userRequestRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;


    @Autowired
    public UserRequestImpl(UserRequestRepository userRequestRepository, ModelMapper modelMapper) {
        super(userRequestRepository, modelMapper, UserRequest.class, UserRequestDTO.class);
    }
    @Override
    public UserRequest saveUserRequest(RequestDto requestDto) throws IOException {
        Category category = categoryRepository.findByCategoryName(requestDto.getCategory().getCategoryName());
        User user = userRepository.findByPhoneNumber(formatPhoneNumber(requestDto.getNumber())).orElse(null);

        if (category != null && user != null) {
            Map<String, Object> profanityResult = checkProfanity(requestDto.getDescription());
            Object hasProfanityObject = profanityResult.get("has_profanity");
            boolean hasProfanity = false; // Default value if not a Boolean

            if (hasProfanityObject instanceof Boolean) {
                hasProfanity = (Boolean) hasProfanityObject;
            } else if (hasProfanityObject instanceof String) {
                hasProfanity = Boolean.parseBoolean((String) hasProfanityObject);
            }

            Map<String, Object> sentimentResult = checkSentiment(requestDto.getDescription());
            Object sentimentObject = sentimentResult.get("sentiment");
            String sentiment = null; // Default value if not a String

            if (sentimentObject instanceof String) {
                sentiment = (String) sentimentObject;
            }

            boolean isPositiveSentiment = sentiment != null && sentiment.contains("POSITIVE");
            boolean isNeutralSentiment = sentiment != null && sentiment.contains("NEUTRAL");
            boolean isNegativeSentiment = sentiment != null && sentiment.contains("NEGATIVE");
            UserRequest userRequest;
            if (isPositiveSentiment && !hasProfanity) {
                // Approved condition: Positive sentiment and no profanity
                userRequest = UserRequest.builder()
                        .needsAdminApproval(false)
                        .title(requestDto.getTitle())
                        .description(requestDto.getDescription())
                        .price(requestDto.getPrice())
                        .category(Collections.singletonList(category))
                        .user(user)
                        .approvedBySystem(true)
                        .build();
            } else if (isNeutralSentiment && !hasProfanity) {
                // Neutral sentiment with no profanity, needs further analysis or default action
                userRequest = UserRequest.builder()
                        .needsAdminApproval(false)
                        .description(requestDto.getDescription())
                        .price(requestDto.getPrice())
                        .category(Collections.singletonList(category))
                        .user(user)
                        .approvedBySystem(false) // Needs further analysis or default action
                        .build();
            } else {
                // Negative sentiment or has profanity
                userRequest = UserRequest.builder()
                        .needsAdminApproval(true) // Needs admin approval for negative sentiment or profanity
                        .description(requestDto.getDescription())
                        .price(requestDto.getPrice())
                        .category(Collections.singletonList(category))
                        .user(user)
                        .approvedBySystem(false)
                        .build();
            }

            UserRequest savedUserRequest = userRequestRepository.save(userRequest);
            // Assuming UserRequestDTO is the DTO representation of UserRequest, convert and return it
            return savedUserRequest;
        } else {
            // Handle cases where category or user is not found
            return null;
        }
    }
}

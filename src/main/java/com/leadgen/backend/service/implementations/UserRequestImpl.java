package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.CategoryDTO;
import com.leadgen.backend.Dto.LocationDTO;
import com.leadgen.backend.Dto.RequestDto;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.Location;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Category category = categoryRepository.findByCategoryName(requestDto.getCategoryy().getCategoryName());
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
                        .category(category)
                        .user(user)
                        .location(requestDto.getLocationModel().toString())
                        .notifiedNumber(0L)
                        .notifiable(Boolean.TRUE)
                        .approvedBySystem(true)
                        .build();
            } else if (isNeutralSentiment && !hasProfanity) {
                // Neutral sentiment with no profanity, needs further analysis or default action
                userRequest = UserRequest.builder()
                        .needsAdminApproval(false)
                        .title(requestDto.getTitle())
                        .description(requestDto.getDescription())
                        .price(requestDto.getPrice())
                        .category(category)
                        .location(requestDto.getLocationModel().toString())
                        .user(user)
                        .notifiedNumber(0L)
                        .notifiable(Boolean.TRUE)
                        .approvedBySystem(true) // Needs further analysis or default action
                        .build();
            } else {
                // Negative sentiment or has profanity
                userRequest = UserRequest.builder()
                        .needsAdminApproval(true)
                        .title(requestDto.getTitle())
                        .description(requestDto.getDescription())
                        .price(requestDto.getPrice())
                        .category(category)
                        .location(requestDto.getLocationModel().toString())
                        .notifiedNumber(0L)
                        .notifiable(Boolean.TRUE)
                        .user(user)
                        .approvedBySystem(false )
                        .build();
            }

            UserRequest savedUserRequest = userRequestRepository.save(userRequest);
            // Assuming UserRequestDTO is the DTO representation of UserRequest, convert and return it
            return savedUserRequest;
        } else {
          throw new RuntimeException("catregory Not found");
        }
    }

    @Override
    public List<RequestDto> getAllUserRequests(String phoneNumber) {
        String phoneNumber1=formatPhoneNumber(phoneNumber);
        List<UserRequest> userRequests = userRequestRepository.findByUserPhoneNumberOrderByCreatedDtDesc(phoneNumber1);
        return mapToRequestDtoList(userRequests);
    }

    private List<RequestDto> mapToRequestDtoList(List<UserRequest> userRequests) {
        return userRequests.stream()
                .map(this::mapToRequestDto)
                .collect(Collectors.toList());
    }

    private Location mapToLocationDomain(LocationDTO locationDTO){
        return Location.builder()
                .administrativeArea(locationDTO.getAdministrativeArea())
                .country(locationDTO.getCountry())
                .id(locationDTO.getId())
                .street(locationDTO.getStreet())
                .subLocality(locationDTO.getSubLocality())
                .SubAdministrativeArea(locationDTO.getSubAdministrativeArea())
                .build();
    }

    private RequestDto mapToRequestDto(UserRequest userRequest) {
        CategoryDTO categoryDTO = mapToCategoryDTO(userRequest.getCategory());


        return RequestDto.builder()
                .description(userRequest.getDescription())
                .createdDate(String.valueOf(userRequest.getCreatedDt()))
                .title(userRequest.getTitle())
                .categoryy(categoryDTO)
                .locationModel(userRequest.getLocation())
                .number(userRequest.getUser().getPhoneNumber())
                .price(userRequest.getPrice())
                .build();
    }

    private CategoryDTO mapToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .backgroundColor(category.getBackgroundColor())
                .icons(category.getIcons())
                .build();
    }

    public static LocationDTO mapToLocationDTO(Location location) {
        return LocationDTO.builder()
                .id(location.getId())
                .locality(location.getLocality())
                .subLocality(location.getSubLocality())
                .street(location.getStreet())
                .country(location.getCountry())
                .deviceId("dda")
                .SubAdministrativeArea(location.getSubAdministrativeArea())
                .administrativeArea(location.getAdministrativeArea())
                .build();
    }
}

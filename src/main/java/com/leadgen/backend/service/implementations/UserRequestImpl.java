package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.*;
import com.leadgen.backend.model.*;
import com.leadgen.backend.repository.CategoryRepository;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.repository.UserRequestRepository;
import com.leadgen.backend.service.UserRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public List<RequestDto> getSellerNotifications(String categoryName) {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfYesterday = LocalDate.now().minusDays(1).atStartOfDay();

        // Convert LocalDateTime to Instant and then to Timestamp
        Instant startTodayInstant = startOfToday.atZone(ZoneId.systemDefault()).toInstant();
        Instant startYesterdayInstant = startOfYesterday.atZone(ZoneId.systemDefault()).toInstant();

        Timestamp startTodayTimestamp = Timestamp.from(startTodayInstant);
        Timestamp startYesterdayTimestamp = Timestamp.from(startYesterdayInstant);

        List<UserRequest> userRequests;
        try {

            Category category = categoryRepository.findByCategoryName(categoryName);
            // Retrieve user requests from the repository
            userRequests = userRequestRepository.findByCategoryAndApprovedBySystemTrueAndNotifiableTrueAndNotifiedNumberGreaterThanAndCreatedDtBetweenOrderByCreatedDtDesc(category,0L, startYesterdayTimestamp, startTodayTimestamp);
        } catch (Exception e) {
            // Handle any exceptions that occur during data retrieval
            e.printStackTrace();
            // Log the error or perform any other necessary actions
            return Collections.emptyList(); // Return an empty list indicating no data found
        }

        // Check if no data is found
        if (userRequests == null || userRequests.isEmpty()) {
            // Handle the case where no data is found
            System.out.println("No user requests found");
            return Collections.emptyList(); // Return an empty list indicating no data found
        }

        // Convert UserRequest entities to RequestDto objects
        return mapToRequestDtoList(userRequests);
    }

    @Override
    public List<UserRequestDTO> getAllRequests() {
        List<UserRequest> userRequests = this.userRequestRepository.findAll();
        List<UserRequestDTO> dtos = new ArrayList<>();

        for(UserRequest request : userRequests){
            dtos.add(convertToDto(request));
        }

        return dtos;
    }

    private UserRequestDTO convertToDto(UserRequest request) {
        return UserRequestDTO.builder()
                .id(request.getId())
                .request(request.getDescription())
                .approvedBySystem(request.getApprovedBySystem())
                .approvedByAdmin(request.getNeedsAdminApproval())
                .price(Long.valueOf(request.getPrice()))
                .category(convertToCategoryDto(request.getCategory()))
                .bids(convertToBidDto(request.getBids()))
                .user(request.getUser())
                .build();
    }

    private List<CategoryDTO> convertToCategoryDto(Category category) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        if(category != null){
            categoryDTOS.add(
                    CategoryDTO.builder()
                            .id(category.getId())
                            .categoryName(category.getCategoryName())
                            .backgroundColor(category.getBackgroundColor())
                            .icons(category.getIcons())
                            .build()
            );

            return categoryDTOS;
        }
        return categoryDTOS;
    }

    private List<BidDTO> convertToBidDto(List<Bid> bids) {
        List<BidDTO> bidDTOS = new ArrayList<>();
        for(Bid bid : bids){
            BidDTO dto = BidDTO.builder()
                    .id(bid.getId())
                    .amount(bid.getAmount())
                    .bidToUserId(bid.getBidToUserId())
                    .bidFromUserId(bid.getBidFromUserId())
                    .build();

            bidDTOS.add(dto);
        }

        return bidDTOS;
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

        UserDTO UserDto = mapToUserDto(userRequest.getUser());
        return RequestDto.builder()
                .description(userRequest.getDescription())
                .createdDate(String.valueOf(userRequest.getCreatedDt()))
                .title(userRequest.getTitle())
                .categoryy(categoryDTO)
                .user(UserDto)
                .locationModel(userRequest.getLocation())
                .number(userRequest.getUser().getPhoneNumber())
                .price(userRequest.getPrice())
                .build();
    }


    private UserDTO mapToUserDto(User user) {
        return UserDTO.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .OTP(user.getOTP())
                .otpFlag(user.getOtpFlag())
                .userType(user.getUserType())
                .id(user.getId()).build();


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

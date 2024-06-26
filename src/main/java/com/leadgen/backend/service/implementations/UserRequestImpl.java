package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.*;
import com.leadgen.backend.model.*;
import com.leadgen.backend.repository.CategoryRepository;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.repository.UserRequestRepository;
import com.leadgen.backend.scheduler.RequestAcceptionNotification;
import com.leadgen.backend.scheduler.RequestDeletionNotification;
import com.leadgen.backend.service.UserRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
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
    RequestDeletionNotification requestDeletionNotification;
    @Autowired
    RequestAcceptionNotification requestAcceptionNotification;


    @Autowired
    public UserRequestImpl(UserRequestRepository userRequestRepository, ModelMapper modelMapper) {
        super(userRequestRepository, modelMapper, UserRequest.class, UserRequestDTO.class);
    }
    @Override
    public UserRequest saveUserRequest(RequestDto requestDto) throws IOException {
        Category category = categoryRepository.findByCategoryName(requestDto.getCategoryy().getCategoryName());
        User user = userRepository.findByEmail(requestDto.getEmail()).orElse(null);

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
            boolean isNeutralSentiment = sentiment != null && sentiment.contains("NEUTRAL") || sentiment != null && sentiment.contains("WEAK")  ;
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
                        .status(true)
                        .acceptedAmount(0L)
                        .accepted(false)
                        .deletedRequest(false)
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
                        .approvedBySystem(true)
                        .acceptedAmount(0L)
                        .status(true)// Needs further analysis or default action
                        .accepted(false)
                        .deletedRequest(false)
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
                        .acceptedAmount(0L)
                        .approvedBySystem(false)
                        .status(false)
                        .accepted(false)
                        .deletedRequest(false)
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
    public List<RequestDto> getAllUserRequests(String email) {

        List<UserRequest> userRequests = userRequestRepository.findByUserEmailOrderByCreatedDtDesc(email);
        List<UserRequest> savedUserRequest = new ArrayList<>();

        for(UserRequest request : userRequests){
//            if(request.getStatus() != null && request.getStatus()){
                savedUserRequest.add(request);
//            }
        }
        return mapToRequestDtoList(savedUserRequest);
    }

    public List<RequestDto> getSellerNotifications(String categoryName) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // Retrieve the category object by its name
        Category category = categoryRepository.findByCategoryName(categoryName);

        // If the category is not found, return an empty list
        if (category == null) {
            return Collections.emptyList();
        }

        List<UserRequest> userRequests;
        try {
            // Retrieve user requests from the repository
            userRequests = userRequestRepository.findByCategoryAndApprovedBySystemTrueAndNotifiedNumberGreaterThanEqualOrderByCreatedDtDesc(
                    category, // Category object
                    0L // notifiedNumber greater than 0
            );
        } catch (Exception e) {
            // Handle any exceptions that occur during data retrieval
            e.printStackTrace();
            // Log the error or perform any other necessary actions
            return Collections.emptyList(); // Return an empty list indicating no data found
        }

        // Filter user requests based on the modified date
        List<UserRequest> filteredUserRequests = userRequests.stream()
                .filter(userRequest -> {
                    LocalDate requestDate = userRequest.getCreatedDt().toLocalDateTime().toLocalDate();
                    return requestDate.equals(today) || requestDate.equals(yesterday);
                })
                .collect(Collectors.toList());

        // Convert UserRequest entities to RequestDto objects
        return mapToRequestDtoList(filteredUserRequests);
    }

    @Override
    public List<UserRequestDTO> getAllRequests() {
        List<UserRequest> userRequests = this.userRequestRepository.getAllUserRequest();
        List<UserRequestDTO> dtos = new ArrayList<>();

        for(UserRequest request : userRequests){
            dtos.add(convertToDto(request));
        }

        return dtos;
    }

    @Override
    public UserRequest setAdminApprovalOfRequest(Long id) {
        Optional<UserRequest> userRequest = this.userRequestRepository.findById(id);

        if(userRequest.isPresent()){
            UserRequest request = userRequest.get();

            request.setNeedsAdminApproval(false);
            request.setApprovedBySystem(true);
            request.setStatus(true);
            this.userRequestRepository.save(request);
            requestAcceptionNotification.notifySellersRequestAcception(request);
            return request;
        }

        return null;
    }

    @Override
    public UserRequest cancelSellerRequest(Long id) {
        Optional<UserRequest> userRequest = this.userRequestRepository.findById(id);

        if(userRequest.isPresent()){
            UserRequest request = userRequest.get();

            request.setStatus(false);
            this.userRequestRepository.save(request);
            return request;
        }
        return null;
    }

    @Override
    public UserRequest deleteSellerRequest(Long id) {
        Optional<UserRequest> userRequest = this.userRequestRepository.findById(id);

        if(userRequest.isPresent()){
            UserRequest request = userRequest.get();

            request.setDeletedRequest(true);
            this.userRequestRepository.save(request);
            requestDeletionNotification.notifySellers(request);
            return request;
        }
        return null;
    }

    @Override
    public Boolean accept(Long id, String emailOfAcceptedSellerBid, Long acceptedAmount) {
        try {
            Optional<UserRequest> userRequestOpt = this.userRequestRepository.findById(id);
            Optional<User> acceptedUserOpt = this.userRepository.findByEmail(emailOfAcceptedSellerBid);

            if (!userRequestOpt.isPresent()) {
                throw new EntityNotFoundException("UserRequest not found with id: " + id);
            }

            UserRequest userRequest = userRequestOpt.get();

            if (userRequest.getAcceptedSeller() == null) {
                if (acceptedUserOpt.isPresent()) {
                    User acceptedUser = acceptedUserOpt.get();

                    userRequest.setAcceptedAmount(acceptedAmount);
                    userRequest.setAccepted(true);
                    userRequest.setAcceptedSeller(acceptedUser);
                    userRequest.setNotifiable(Boolean.FALSE);


                    this.userRequestRepository.save(userRequest);

                    return true;
                } else {
                    throw new EntityNotFoundException("User not found with email: " + emailOfAcceptedSellerBid);
                }
            } else {
                throw new RuntimeException("Accepted seller already exists for this UserRequest");
            }
        } catch (RuntimeException e) {

            System.err.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // Log and handle generic exceptions
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserRequestDTO> getAllDeletedRequests() {
        List<UserRequest> userRequests = this.userRequestRepository.getAllUserRequestDeleted();
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
        UserDTO userDTO = userRequest.getAcceptedSeller() == null ? null : mapToUserDto(userRequest.getAcceptedSeller());
        UserDTO UserDto = mapToUserDto(userRequest.getUser());
        return RequestDto.builder()
                .id(userRequest.getId())
                .description(userRequest.getDescription())
                .createdDate(String.valueOf(userRequest.getCreatedDt()))
                .title(userRequest.getTitle())
                .categoryy(categoryDTO)
                .user(UserDto)
                .acceptedAmount(userRequest.getAcceptedAmount())
                .acceptedSeller(userDTO)
                .accepted(userRequest.getAccepted().toString())
                .locationModel(userRequest.getLocation())
                .email(userRequest.getUser().getEmail())
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
                .uid(user.getUid())
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

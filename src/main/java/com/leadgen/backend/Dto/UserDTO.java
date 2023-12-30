package com.leadgen.backend.Dto;

import com.leadgen.backend.enums.UserType;
import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.Location;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.model.UserReviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;

    @NotNull(message = "First name cannot be null")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String userName;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    @NotEmpty(message = "device id cannot be empty")
    private String deviceId;

    private Long OTP;

    private Boolean otpFlag;

    private String nationalIdentificationNumber;

    private byte[] profilePic;
    private List<LocationDTO> locations;;


    private List<UserReviews> reviews; //one user revibviw will have many user.


    private UserType userType;

    private List<Category> sellingCategory;

    private List<UserRequest> userRequests;
}

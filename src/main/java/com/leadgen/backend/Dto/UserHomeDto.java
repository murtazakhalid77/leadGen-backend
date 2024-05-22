package com.leadgen.backend.Dto;

import com.leadgen.backend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserHomeDto {
    String firstName;
    String lastName;
    String email;
    String adress;
    String uid;
    String phoneNumber;
    List<String> categories;
    String profilePicPath;
    String cnic;
    UserType userType;

}

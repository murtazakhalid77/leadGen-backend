package com.leadgen.backend.Dto;

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
    String email;
    String adress;
    String uid;
    String phoneNumber;
    List<String> categories;
    String profilePicPath;
    String userType;

}

package com.leadgen.backend.service;

import com.leadgen.backend.Dto.RegisterDto;
import com.leadgen.backend.Dto.UserDTO;
import com.leadgen.backend.Dto.UserTypeDto;
import com.leadgen.backend.model.User;

import java.util.List;

import com.leadgen.backend.Dto.UserHomeDto;

public interface UserService extends GenericService<UserDTO>{
    Boolean registerUser(RegisterDto registerDto);

    List<User> finduserByStatus(Boolean status);
    UserHomeDto getLoggedInUser(String email);

    UserTypeDto getUserType(String email) ;

    String forgotPassword(String number);
    User updatePassword(String number, String password);
    User updateUserInformation(String name, String updatedPhone, String email,String imagePath);
    User setUserSellingCategory(String[] category, String phoneNumber);

    User setUserType(String userType, String email);
}

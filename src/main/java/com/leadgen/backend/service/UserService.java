package com.leadgen.backend.service;

import com.leadgen.backend.Dto.*;
import com.leadgen.backend.model.User;

import java.util.List;

public interface UserService extends GenericService<UserDTO>{
    Boolean registerUser(RegisterDto registerDto);

    List<User> finduserByStatus(Boolean status);
    UserHomeDto getLoggedInUser(String email);

    UserTypeDto getUserType(String email) ;

    OtpAndPassword forgotPassword(String number) throws Exception;
    User updatePassword(String number, String password) throws Exception;
    User updateUserInformation(String name, String lastName, String updatedPhone, String updatedEmail, String email);
    User setUserSellingCategory(String[] category, String phoneNumber);

    User setUserType(String userType, String email);
}

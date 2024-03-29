package com.leadgen.backend.service;

import com.leadgen.backend.Dto.RegisterDto;
import com.leadgen.backend.Dto.UserDTO;
import com.leadgen.backend.Dto.UserTypeDto;
import com.leadgen.backend.model.User;

import java.util.List;
import java.util.Optional;
import com.leadgen.backend.Dto.UserHomeDto;

public interface UserService extends GenericService<UserDTO>{
    Boolean registerUser(RegisterDto registerDto);

    List<User> finduserByStatus(Boolean status);
    UserHomeDto getLoggedInUser(String phoneNumber);

    UserTypeDto getUserType(String phoneNumber) ;

    String forgotPassword(String number);
    User updatePassword(String number, String password);
    User updateUserInformation(String name, String updatedPhone, String email, String userPhone);
}

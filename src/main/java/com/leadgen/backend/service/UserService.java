package com.leadgen.backend.service;

import com.leadgen.backend.Dto.RegisterDto;
import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.Dto.UserDTO;
import com.leadgen.backend.Dto.UserHomeDto;

public interface UserService extends GenericService<UserDTO>{
    Boolean registerUser(RegisterDto registerDto);
    UserHomeDto getLoggedInUser(String phoneNumber);
}

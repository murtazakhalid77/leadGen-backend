package com.leadgen.backend.service;

import com.leadgen.backend.Dto.RequestDto;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.model.UserRequest;

import java.io.IOException;

public interface UserRequestService extends GenericService<UserRequestDTO> {
    UserRequest saveUserRequest(RequestDto requestDto) throws IOException;
}

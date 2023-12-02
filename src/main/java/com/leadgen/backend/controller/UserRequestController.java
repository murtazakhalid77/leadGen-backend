package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.service.TestServiceInterface;
import com.leadgen.backend.service.UserRequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userRequest/")
public class UserRequestController extends GenericController<UserRequestDTO> {

    public UserRequestController(UserRequestService userRequestService) {
        super(userRequestService);
    }
}

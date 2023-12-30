package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.RequestDto;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.service.TestServiceInterface;
import com.leadgen.backend.service.UserRequestService;
import com.leadgen.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/userRequest/")
public class UserRequestController extends GenericController<UserRequestDTO> {

    @Autowired
    UserRequestService userRequestService;
    public UserRequestController(UserRequestService userRequestService) {
        super(userRequestService);
    }

    @PostMapping("saverequest")
    public ResponseEntity<UserRequest> create(@RequestBody RequestDto requestDto) throws IOException {
      UserRequest userRequest=  userRequestService.saveUserRequest(requestDto);
        return new ResponseEntity<UserRequest>(userRequest, HttpStatus.CREATED);
    }




}

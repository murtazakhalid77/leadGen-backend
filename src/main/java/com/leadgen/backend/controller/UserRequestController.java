package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.RequestDto;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.repository.UserRequestRepository;
import com.leadgen.backend.service.TestServiceInterface;
import com.leadgen.backend.service.UserRequestService;
import com.leadgen.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.leadgen.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/userRequest/")
@CrossOrigin("*")
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


    @GetMapping("/getAllUserRequests/{email}")
    public ResponseEntity<List<RequestDto>> getAllUserRequests(@PathVariable("email") String email) {
        List<RequestDto> userRequests = userRequestService.getAllUserRequests(email);
        return new ResponseEntity<>(userRequests, HttpStatus.OK);
    }

    @GetMapping("/getAllSellerRequest/{categoryName}")
    public ResponseEntity<List<RequestDto>> getAllSellerRequest(@PathVariable("categoryName") String categoryName) {
        List<RequestDto> userRequests = userRequestService.getSellerNotifications(categoryName);
        return new ResponseEntity<List<RequestDto>>(userRequests, HttpStatus.OK);
    }

    @GetMapping("/getAllRequests")
    public ResponseEntity<?> getAllRequests(){
        List<UserRequestDTO> requestDTOS = this.userRequestService.getAllRequests();
        return new ResponseEntity<List<UserRequestDTO>>(requestDTOS, HttpStatus.OK);
    }

}

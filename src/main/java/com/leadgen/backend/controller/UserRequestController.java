package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.RequestDto;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.Dto.UserTypeDto;
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
      UserRequest userRequest =  userRequestService.saveUserRequest(requestDto);
        return new ResponseEntity<UserRequest>(userRequest, HttpStatus.CREATED);
    }


    @GetMapping("/getAllUserRequests/{email}")
    public ResponseEntity<List<RequestDto>> getAllUserRequests(@PathVariable("email") String email) {
        List<RequestDto> userRequests = userRequestService.getAllUserRequests(email);
        return  ResponseEntity.ok(userRequests);
    }

    @GetMapping("/getAllSellerRequest/{categoryName}")
    public ResponseEntity<List<RequestDto>> getAllSellerRequest(@PathVariable("categoryName") String categoryName) {
        List<RequestDto> userRequests = userRequestService.getSellerNotifications(categoryName);
        return new ResponseEntity<List<RequestDto>>(userRequests, HttpStatus.OK);
    }
//continue from here the work is done show accpeted bid amount on the caard
    @GetMapping("/getAllRequests")
    public ResponseEntity<?> getAllRequests(){
        List<UserRequestDTO> requestDTOS = this.userRequestService.getAllRequests();
        return new ResponseEntity<List<UserRequestDTO>>(requestDTOS, HttpStatus.OK);
    }

    @GetMapping("/getAllDeletedRequests")
    public ResponseEntity<?> getAllDeletedRequests(){
        List<UserRequestDTO> requestDTOS = this.userRequestService.getAllDeletedRequests();
        return new ResponseEntity<List<UserRequestDTO>>(requestDTOS, HttpStatus.OK);
    }

    @PutMapping("/setApproval/{requestId}")
    public ResponseEntity<?> setRequestAdminApproval(@PathVariable Long requestId){
        try {
            UserRequest userRequest = this.userRequestService.setAdminApprovalOfRequest(requestId);
            if(userRequest != null){
                return ResponseEntity.ok(userRequest);
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get user request. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/cancelRequest/{requestId}")
    public ResponseEntity<?> cancelSellerRequest(@PathVariable Long requestId){
        try {
            UserRequest userRequest = this.userRequestService.cancelSellerRequest(requestId);
            if(userRequest != null){
                return ResponseEntity.ok(userRequest);
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get user request. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/accept/{requestId}/{emailOFAcceptedUser}/{bidAmount}")
    public ResponseEntity<?> accept(@PathVariable Long requestId, @PathVariable String emailOFAcceptedUser, @PathVariable String bidAmount) {
        try {
            double bidAmountDouble = Double.parseDouble(bidAmount);
            long bidAmountLong = (long) bidAmountDouble;

            boolean userRequest = this.userRequestService.accept(requestId, emailOFAcceptedUser, bidAmountLong);

            if (userRequest) {
                return ResponseEntity.ok(userRequest);
            } else {
                return ResponseEntity.ok(false);

            }
        } catch (NumberFormatException e) {
           throw  new RuntimeException("some error occured");
        }
    }

    @PutMapping("/deleteRequest/{requestId}")
    public ResponseEntity<?> deleteSellerRequest(@PathVariable Long requestId){
        try {
            UserRequest userRequest = this.userRequestService.deleteSellerRequest(requestId);
            if(userRequest != null){
                return ResponseEntity.ok(userRequest);
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get user request. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

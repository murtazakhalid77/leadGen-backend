package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.*;
import com.leadgen.backend.model.User;
import com.leadgen.backend.service.TestServiceInterface;
import com.leadgen.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin("*")
public class UserController extends GenericController<UserDTO> {

    private final UserService userService;


    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @PutMapping("/save")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        try {

            if (userService.registerUser(registerDto)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to set user details. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getLoggedInUser/{phoneNumber}")
    public ResponseEntity<?> registerUser(@PathVariable String phoneNumber) {
        try {
            UserHomeDto userHomeDto = userService.getLoggedInUser(phoneNumber);
            return ResponseEntity.ok().body(userHomeDto);
        } catch (Exception e) {
            // Log the exception for debugging purposes


            // Handle the exception and return an appropriate error message
            String errorMessage = "Failed to get logged-in user details.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/get-status/{status}")
    public ResponseEntity<?> getUserByStatus(@PathVariable Boolean status){
        try {
            List<User> user = userService.finduserByStatus(status);
            if(!user.isEmpty()){
                return ResponseEntity.ok(user);
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get user. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getUserType/{phoneNumber}")
    public ResponseEntity<?> getUserTypeByPhoneNumber(@PathVariable String phoneNumber){
        try {
            UserTypeDto user = userService.getUserType(phoneNumber);
            if(user != null){
                return ResponseEntity.ok(user);
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get user. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updatePassword/{number}/{password}")
    public ResponseEntity<?> updateUserPassword(@PathVariable String number,@PathVariable String password){
        try {
            User user = userService.updatePassword(number, password);
            if(user != null){
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get user. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateProfile/{name}/{updatePhone}/{email}/{userPhone}")
    public ResponseEntity<?> updateUserInformation(@PathVariable String name,@PathVariable String updatePhone,
                                                   @PathVariable String email, @PathVariable String userPhone){
        try {
            User user = userService.updateUserInformation(name, updatePhone, email, userPhone);
            if(user != null){
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user information. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{userPhone}")
    public ResponseEntity<?> setUserCategory(@RequestBody String[] selectedCategory, @PathVariable String userPhone){
        try {
            User user = userService.setUserSellingCategory(selectedCategory, userPhone);
            if(user != null){
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to set selling category. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

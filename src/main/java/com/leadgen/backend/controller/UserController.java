package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.RegisterDto;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserDTO;
import com.leadgen.backend.Dto.UserHomeDto;
import com.leadgen.backend.service.TestServiceInterface;
import com.leadgen.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")

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
}

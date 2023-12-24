package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.RegisterDto;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserDTO;
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
@CrossOrigin(origins = "http://localhost:4200")
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
}

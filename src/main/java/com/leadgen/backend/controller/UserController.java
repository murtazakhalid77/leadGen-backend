package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.Dto.UserDTO;
import com.leadgen.backend.service.TestServiceInterface;
import com.leadgen.backend.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController extends GenericController<UserDTO> {

    public UserController(UserService userService) {
        super(userService);
    }
}

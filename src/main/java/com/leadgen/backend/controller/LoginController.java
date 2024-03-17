package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.LoginCredentials;
import com.leadgen.backend.helpers.HelperClass;
import com.leadgen.backend.security.util.AuthenticationResponse;
import com.leadgen.backend.security.util.JwtUtil;
import com.leadgen.backend.service.UserService;
import com.leadgen.backend.service.implementations.MyUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginCredentials loginCredentials) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(HelperClass.formatPhoneNumber(loginCredentials.getPhoneNumber()),loginCredentials.getPassword())
            );
        }
        catch(BadCredentialsException e){
            e.printStackTrace();
            logger.warn("User Not found....");
            throw new Exception("Incorrect Username or Password ! ",e);
        }

        UserDetails userDetails = myUserDetailService.loadUserByUsername(HelperClass.formatPhoneNumber(loginCredentials.getPhoneNumber()));
        String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @PostMapping("user/forgot-password/{number}")
    public ResponseEntity<String> forgotPassword(@PathVariable String number) {
        try {
            String otpSent = userService.forgotPassword(number);
            if (otpSent != null) {
                return ResponseEntity.ok(otpSent);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

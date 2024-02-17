package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.LoginCredentials;
import com.leadgen.backend.helpers.HelperClass;
import com.leadgen.backend.security.util.AuthenticationResponse;
import com.leadgen.backend.security.util.JwtUtil;
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

//    @PostMapping("user/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
//        userService.forgotPassword(email);
//        return new ResponseEntity<>("Password reset email sent successfully.", HttpStatus.OK);
//    }
//
//    @PostMapping("user/reset-password")
//    public ResponseEntity<String> resetPassword(
//            @RequestParam String email,
//            @RequestParam String resetCode,
//            @RequestParam String newPassword) {
//        userService.resetPassword(email, resetCode, newPassword);
//        return new ResponseEntity<>("Password reset successfully.", HttpStatus.OK);
//    }
}

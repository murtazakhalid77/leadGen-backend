package com.leadgen.backend.controller;

import com.leadgen.backend.exception.UserExistException;
import com.leadgen.backend.service.OtpService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class OTPcontroller {
    private static final Logger logger = LoggerFactory.getLogger(OTPcontroller.class);
    private final OtpService otpService;
    @PostMapping("/sendotp/{number}")
    public ResponseEntity<?> sendOtp(@PathVariable String number) {
        try {
            String otpSent = otpService.sendOtp(number);

            if (otpSent != null) {
                logger.info(otpSent);
                return ResponseEntity.ok(otpSent);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("/credentails/{password}/{number}")
    public ResponseEntity<?> createPassword(@PathVariable String password,@PathVariable String number) {
        try {
            Boolean cratedPassword = otpService.cratePassword(password,number);
            if (cratedPassword) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to set password. Please try again.");
            }
        } catch (Exception e) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}

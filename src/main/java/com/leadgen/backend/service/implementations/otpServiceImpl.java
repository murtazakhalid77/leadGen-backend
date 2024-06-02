package com.leadgen.backend.service.implementations;

import com.leadgen.backend.configuration.OtpConfiguration;
import com.leadgen.backend.model.User;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.service.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.leadgen.backend.helpers.HelperClass.formatPhoneNumber;
import static com.leadgen.backend.helpers.HelperClass.generateRandomOTP;

@Service
@AllArgsConstructor
public class otpServiceImpl implements OtpService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final OtpConfiguration otpConfiguration;
    private final UserRepository userRepository;

    @Override
    public String sendOtp(String email) {
        String otp = generateRandomOTP();
        String otpMessage = "Your OTP is: " + otp;
//        String formatPhoneNumber = formatPhoneNumber(phoneNumber);
        Optional<User> existingUser = userRepository.findByEmail(email);

        System.out.println(otp);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this phone number already exists");

        }
            else {
//           boolean otpCheck = otpConfiguration.sendSMS("Lead Gen", formatPhoneNumber, otpMessage);
             if (true) {
                 User newUser = User.builder()
                         .OTP(otp)
                         .email(email)
                         .status(true)
                         .otpFlag(false)
                         .build();
                 userRepository.save(newUser);

             }
                return otp;
            }
    }

    @Override
    public Boolean cratePassword(String password, String email) {

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            user.get().setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(user.get());
            return true;
        }
        else {
            throw new RuntimeException("the user doesnt exist against this number");

        }

    }


}

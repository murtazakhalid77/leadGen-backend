package com.leadgen.backend.service.implementations;

import com.leadgen.backend.configuration.OtpConfiguration;
import com.leadgen.backend.model.User;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.security.util.PasswordEncryptionUtil;
import com.leadgen.backend.service.OtpService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import java.util.Optional;

import static com.leadgen.backend.helpers.HelperClass.formatPhoneNumber;
import static com.leadgen.backend.helpers.HelperClass.generateRandomOTP;


@Service
@AllArgsConstructor
public class otpServiceImpl implements OtpService {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SecretKey secretKey;
    private final OtpConfiguration otpConfiguration;
    private final UserRepository userRepository;

    @Override
    public String sendOtp(String email) {
        String otp = generateRandomOTP();
        String otpMessage = "Your OTP is: " + otp;

        Optional<User> existingUser = userRepository.findByEmail(email);

        System.out.println(otp);
        if (existingUser.isPresent()) {
            throw new RuntimeException("User with this phone number already exists");

        }
            else {
           boolean otpCheck = sendSimpleEmail(email, otpMessage);
             if (otpCheck) {
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
    public Boolean cratePassword(String password, String email) throws Exception {

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){

            user.get().setPassword(PasswordEncryptionUtil.encrypt(password,secretKey));
            userRepository.save(user.get());
            return true;
        }
        else {
            throw new RuntimeException("the user doesnt exist against this number");

        }

    }

    public  boolean sendSimpleEmail(String toEmail,

                                String otp
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fa20bscs0108@maju.edu.pk");
        message.setTo(toEmail);
        message.setText("Greetings!\n" +
                "\n" +
                "To continue with your registration, here's your OTP to be entered: "+otp+
                "\n" +
                "Do not share this OTP with anyone else.\n" +
                "\n" +
                "Regards,\n" +
                "Team Lead Gen");
        message.setSubject("Lead gen OTP Verification");
        mailSender.send(message);
        System.out.println("Mail Send...");
        return  true;

    }


}

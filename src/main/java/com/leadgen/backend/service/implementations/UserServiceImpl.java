package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.*;
import com.leadgen.backend.configuration.OtpConfiguration;
import com.leadgen.backend.enums.UserType;
import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.User;
import com.leadgen.backend.repository.CategoryRepository;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.security.util.PasswordEncryptionUtil;
import com.leadgen.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.leadgen.backend.helpers.HelperClass.*;


@Service

public class UserServiceImpl extends GenericServiceImpl<User, UserDTO> implements UserService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OtpConfiguration otpConfiguration;
    @Autowired
    private SecretKey secretKey;
    @Autowired
    public UserServiceImpl(JpaRepository<User, Long> repository, ModelMapper modelMapper) {
        super(repository, modelMapper, User.class, UserDTO.class);


    }

    public  boolean sendForgotPasswordEmail(String toEmail,

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

    @Override
    public Boolean registerUser(RegisterDto registerDto) {
        Optional<User> userByCnic = userRepository.findByNationalIdentificationNumber(registerDto.getCnic());
        if (userByCnic.isPresent()) {
            throw new RuntimeException("User already exists with this CNIC Number: " + registerDto.getCnic());
        }

        Optional<User> userByPhoneNumber = userRepository.findByEmail(registerDto.getEmail());
        userByPhoneNumber.ifPresent(user -> {
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setNationalIdentificationNumber(registerDto.getCnic());
            user.setPhoneNumber(formatPhoneNumber(registerDto.getPhoneNumber()));
            user.setUid(registerDto.getUid());
            user.setDeviceId(registerDto.getFcmToken());
            userRepository.save(user);

        });
        return true;
    }

    @Override
    public UserHomeDto getLoggedInUser(String email) {
        Optional<User> userByPhoneNumber = userRepository.findByEmail(email);

        if (userByPhoneNumber.isPresent()) {
            User user = userByPhoneNumber.get();


                return UserHomeDto.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .cnic(user.getNationalIdentificationNumber())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .uid(user.getUid())
                        .userType(user.getUserType().toString())
                        .profilePicPath(user.getProfilePic())
                        .categories(user.getSellingCategory().stream().map(p->p.getCategoryName()).collect(Collectors.toList()))
                        .build();


        } else {
            throw new RuntimeException("User Not Found With The abovePhoneNumber");
        }
    }

    @Override
    public UserTypeDto getUserType(String email) {
        Optional<User> userDetail = userRepository.findByEmail(email);

        if(userDetail.isPresent()){
            User user = userDetail.get();

            return UserTypeDto.builder()
                    .userType(user.getUserType())
                    .build();
        }else {
            throw new RuntimeException("User Not Found With The abovePhoneNumber");
        }
    }

    public OtpAndPassword forgotPassword(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            String otp = generateRandomOTP();
            String otpMessage = "Your Forgot Password OTP is: " + otp;

            // Decrypt the user's password
            String password = PasswordEncryptionUtil.decrypt(user.get().getPassword(), secretKey);
            boolean otpCheck = sendForgotPasswordEmail(email, otpMessage);
            if (otpCheck) {
                User newUser = user.get();
                newUser.setOTP(otp);
                newUser.setStatus(true);
                newUser.setOtpFlag(false);

                userRepository.save(newUser);
            }

            return OtpAndPassword.builder()
                    .otp(otp)
                    .password(password)
                    .build();
        } else {
            throw new RuntimeException("User Not Found");
        }
    }


    @Override
    public User updatePassword(String email, String password) throws Exception {
        Optional<User> userInfo = userRepository.findByEmail(email);

        if(userInfo.isPresent()){

            User user = userInfo.get();

            user.setPassword(PasswordEncryptionUtil.encrypt(password,secretKey));
            userRepository.save(user);

            return user;
        }

        return null;
    }

    @Override
    public User updateUserInformation(String name, String lastName, String updatedPhone, String updatedEmail, String email) {
        Optional<User> userInfo = userRepository.findByEmail(email);


        if(userInfo.isPresent()){
            User user = userInfo.get();
            if(formattedPhoneNumberFromDatabase(user.getPhoneNumber()).equalsIgnoreCase(updatedPhone)
                    && !user.getEmail().equalsIgnoreCase(updatedEmail)){
                user.setFirstName(name);
                user.setEmail(updatedEmail);
                user.setLastName(lastName);
//                user.setProfilePic(imagePath);
                userRepository.save(user);
                return user;
            }
            else if(user.getEmail().equalsIgnoreCase(updatedEmail)
                    && !formattedPhoneNumberFromDatabase(user.getPhoneNumber()).equalsIgnoreCase(updatedPhone)){
                user.setFirstName(name);
                user.setLastName(lastName);
                user.setPhoneNumber(formatPhoneNumber(updatedPhone));
//                user.setProfilePic(imagePath);
                userRepository.save(user);
                return user;
            }
            else if(formattedPhoneNumberFromDatabase(user.getPhoneNumber()).equalsIgnoreCase(updatedPhone)
                    && user.getEmail().equalsIgnoreCase(updatedEmail)) {
                user.setFirstName(name);
                user.setLastName(lastName);
                user.setPhoneNumber(formatPhoneNumber(updatedPhone));
                user.setEmail(updatedEmail);
//                user.setProfilePic(imagePath);
                userRepository.save(user);
                return user;
            }

        }

        return null;
    }

    @Override
    public User setUserSellingCategory(String[] category, String email) {
        Optional<User> userInfo = userRepository.findByEmail(email);

        if(userInfo.isPresent()){
            User user = userInfo.get();
            Set<Category> categoryList = user.getSellingCategory();
            for(String cat : category){
                Category category1 = categoryRepository.findByCategoryName(cat);
                if(!user.getSellingCategory().contains(category1)){
                    categoryList.add(category1);
                }
            }
            user.setSellingCategory(categoryList);
            userRepository.save(user);

            return user;
        }

        return null;
    }

    @Override
    public User setUserType(String userType, String email) {
        Optional<User> userInfo = userRepository.findByEmail(email);


        if(userInfo.isPresent()){
            User user = userInfo.get();
            UserType type = user.getUserType();
            UserType userTypeEnum = UserType.valueOf(userType.toUpperCase());

            if(type == null){
                user.setUserType(userTypeEnum);
                userRepository.save(user);
                return user;
            }else if(userTypeEnum != user.getUserType()) {
                user.setUserType(UserType.BOTH);
                userRepository.save(user);
                return user;
            }

        }
        return null;
    }


    @Override
    public List<User> finduserByStatus(Boolean statusValue) {
        return this.userRepository.findByStatus(statusValue);
    }
}


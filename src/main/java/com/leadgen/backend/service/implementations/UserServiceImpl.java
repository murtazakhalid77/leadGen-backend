package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.*;
import com.leadgen.backend.configuration.OtpConfiguration;
import com.leadgen.backend.enums.UserType;
import com.leadgen.backend.model.Category;
import com.leadgen.backend.model.Location;
import com.leadgen.backend.model.User;
import com.leadgen.backend.repository.CategoryRepository;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.leadgen.backend.helpers.HelperClass.*;

@Service

public class UserServiceImpl extends GenericServiceImpl<User, UserDTO> implements UserService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    OtpConfiguration otpConfiguration;

    @Autowired
    public UserServiceImpl(JpaRepository<User, Long> repository, ModelMapper modelMapper) {
        super(repository, modelMapper, User.class, UserDTO.class);


    }

    @Override
    public Boolean registerUser(RegisterDto registerDto) {
        Optional<User> userByCnic = userRepository.findByNationalIdentificationNumber(registerDto.getCnic());
        if (userByCnic.isPresent()) {
            throw new RuntimeException("User already exists with this CNIC Number: " + registerDto.getCnic());
        }

        Optional<User> userByPhoneNumber = userRepository.findByPhoneNumber(formatPhoneNumber(registerDto.getPhoneNumber()));
        userByPhoneNumber.ifPresent(user -> {
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setNationalIdentificationNumber(registerDto.getCnic());
            user.setEmail(registerDto.getEmail());
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
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
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

    @Override
    public String forgotPassword(String number) {
        Optional<User> user = userRepository.findByPhoneNumber(formatPhoneNumber(number));

        if (user.isPresent()){
            String otp = generateRandomOTP();
            String otpMessage = "Your Forgot Password OTP is: " + otp;
            String formatPhoneNumber = formatPhoneNumber(number);

            boolean otpCheck = otpConfiguration.sendSMS("Lead Gen", formatPhoneNumber, otpMessage);
            if (otpCheck) {
                User newUser = user.get();
                newUser.setOTP(otp);
                newUser.setStatus(true);
                newUser.setOtpFlag(false);

                userRepository.save(newUser);

            }
            return otp;
        }
        else {
            throw new RuntimeException("User Not Found");
        }

    }

    @Override
    public User updatePassword(String number, String password) {
        Optional<User> userInfo = userRepository.findByPhoneNumber(formatPhoneNumber(number));

        if(userInfo.isPresent()){
            User user = userInfo.get();

            user.setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(user);

            return user;
        }

        return null;
    }

    @Override
    public User updateUserInformation(String name, String updatedPhone, String email, String userPhone) {
        Optional<User> userInfo = userRepository.findByPhoneNumber(formatPhoneNumber(userPhone));


        if(userInfo.isPresent()){
            User user = userInfo.get();
            if(formattedPhoneNumberFromDatabase(user.getPhoneNumber()).equalsIgnoreCase(updatedPhone)
                    && !user.getEmail().equalsIgnoreCase(email)){
                user.setFirstName(name);
                user.setEmail(email);
                userRepository.save(user);
                return user;
            }
            else if(user.getEmail().equalsIgnoreCase(email)
                    && !formattedPhoneNumberFromDatabase(user.getPhoneNumber()).equalsIgnoreCase(updatedPhone)){
                user.setFirstName(name);
                user.setPhoneNumber(formatPhoneNumber(updatedPhone));
                userRepository.save(user);
                return user;
            }
            else if(formattedPhoneNumberFromDatabase(user.getPhoneNumber()).equalsIgnoreCase(updatedPhone)
                    && user.getEmail().equalsIgnoreCase(email)) {
                user.setFirstName(name);
                user.setPhoneNumber(formatPhoneNumber(updatedPhone));
                user.setEmail(email);
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


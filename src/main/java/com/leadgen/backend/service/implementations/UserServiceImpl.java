package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.*;
import com.leadgen.backend.configuration.OtpConfiguration;
import com.leadgen.backend.model.Location;
import com.leadgen.backend.model.User;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.leadgen.backend.helpers.HelperClass.formatPhoneNumber;
import static com.leadgen.backend.helpers.HelperClass.generateRandomOTP;

@Service

public class UserServiceImpl extends GenericServiceImpl<User, UserDTO> implements UserService {


    @Autowired
    UserRepository userRepository;
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
            userRepository.save(user);

        });
        return true;
    }

    @Override
    public UserHomeDto getLoggedInUser(String phoneNumber) {
        Optional<User> userByPhoneNumber = userRepository.findByPhoneNumber(formatPhoneNumber(phoneNumber));

        if (userByPhoneNumber.isPresent()) {
            User user = userByPhoneNumber.get();
            List<Location> locations = user.getLocations();

            Location location = (locations != null && !locations.isEmpty()) ? locations.get(0) : null;

            if (location != null) {
                String address = location.getLocality() + location.getStreet() + location.getSubLocality() +
                        location.getAdministrativeArea() + location.getCountry();

                return UserHomeDto.builder()
                        .firstName(user.getFirstName())
                        .email(user.getEmail())
                        .adress(address)
                        .build();
            } else {
                throw new RuntimeException("Location details are null for the user");
            }
        } else {
            throw new RuntimeException("User Not Found With The abovePhoneNumber");
        }
    }

    @Override
    public UserTypeDto getUserType(String phoneNumber) {
        Optional<User> userDetail = userRepository.findByPhoneNumber(formatPhoneNumber(phoneNumber));

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
        User newUser = User.builder()
                .OTP(otp)
                .phoneNumber(formatPhoneNumber)
                .status(true)
                .otpFlag(false)
                .build();
        userRepository.save(newUser);

    }
    return otp;
}
else {
    throw new RuntimeException("User Not Found");
}

    }


    @Override
    public List<User> finduserByStatus(Boolean statusValue) {
        return this.userRepository.findByStatus(statusValue);
    }
}


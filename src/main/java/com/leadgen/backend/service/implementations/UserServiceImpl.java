package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.RegisterDto;
import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.Dto.UserDTO;
import com.leadgen.backend.model.SubCategory;
import com.leadgen.backend.model.User;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.service.SubCategoryService;
import com.leadgen.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.leadgen.backend.helpers.HelperClass.formatPhoneNumber;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserDTO> implements UserService {


    @Autowired
    UserRepository userRepository;

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
}


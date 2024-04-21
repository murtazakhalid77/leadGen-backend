package com.leadgen.backend.service.implementations;


import com.leadgen.backend.Dto.CustomUserDetail;
import com.leadgen.backend.helpers.HelperClass;
import com.leadgen.backend.model.User;
import com.leadgen.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

           Optional<User> user = userRepository.findByEmail(email);
            if(user.isEmpty()) {
                throw new RuntimeException("Wrong Credentials" + email);
            }
            return new CustomUserDetail(user.get());
    }

}


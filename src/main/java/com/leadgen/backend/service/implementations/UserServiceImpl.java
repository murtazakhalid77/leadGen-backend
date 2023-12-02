package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.Dto.UserDTO;
import com.leadgen.backend.model.SubCategory;
import com.leadgen.backend.model.User;
import com.leadgen.backend.service.SubCategoryService;
import com.leadgen.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, UserDTO> implements UserService {

    @Autowired
    public UserServiceImpl(JpaRepository<User, Long> repository, ModelMapper modelMapper) {
        super(repository, modelMapper, User.class, UserDTO.class);
    }
}

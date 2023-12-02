package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.SubCategoryDTO;
import com.leadgen.backend.Dto.UserRequestDTO;
import com.leadgen.backend.model.SubCategory;
import com.leadgen.backend.model.UserRequest;
import com.leadgen.backend.repository.SubCategoryRepository;
import com.leadgen.backend.repository.UserRequestRepository;
import com.leadgen.backend.service.UserRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRequestImpl extends GenericServiceImpl<UserRequest,UserRequestDTO> implements UserRequestService {
    @Autowired
    public UserRequestImpl(UserRequestRepository userRequestRepository, ModelMapper modelMapper) {
        super(userRequestRepository, modelMapper, UserRequest.class, UserRequestDTO.class);
    }
}

package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.UserReviewsDTO;
import com.leadgen.backend.model.UserReviews;
import com.leadgen.backend.repository.UserReviewsRepository;
import com.leadgen.backend.service.UserReviewsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserReviewsServiceImpl extends GenericServiceImpl<UserReviews, UserReviewsDTO> implements UserReviewsService {
   @Autowired
    public UserReviewsServiceImpl(UserReviewsRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper, UserReviews.class, UserReviewsDTO.class);
    }
}

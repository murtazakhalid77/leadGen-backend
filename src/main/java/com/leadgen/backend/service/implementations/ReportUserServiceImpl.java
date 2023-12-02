package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.ReportUserDTO;
import com.leadgen.backend.model.ReportUser;
import com.leadgen.backend.repository.UserReportRepository;
import com.leadgen.backend.service.RepostUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportUserServiceImpl extends GenericServiceImpl<ReportUser, ReportUserDTO> implements RepostUserService {
   @Autowired
    public ReportUserServiceImpl(UserReportRepository userReportRepository, ModelMapper modelMapper) {
        super(userReportRepository, modelMapper,ReportUser.class, ReportUserDTO.class);
    }
}

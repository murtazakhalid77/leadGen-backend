package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.model.Test;
import com.leadgen.backend.repository.TestRepository;
import com.leadgen.backend.service.TestServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

@Service
public class TestSrviceImpl extends GenericServiceImpl<Test, TestDTO> implements TestServiceInterface {

    @Autowired
    public TestSrviceImpl(TestRepository repository,ModelMapper modelMapper) {
        super(repository, modelMapper, Test.class, TestDTO.class);
    }
}

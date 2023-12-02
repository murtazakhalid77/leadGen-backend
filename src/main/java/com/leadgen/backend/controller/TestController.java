package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.service.TestServiceInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/")
public class TestController  extends GenericController<TestDTO> {

    public TestController(TestServiceInterface testServiceInterface) {
        super(testServiceInterface);
    }
}

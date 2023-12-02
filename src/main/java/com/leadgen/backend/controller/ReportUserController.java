package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.ReportUserDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.service.RepostUserService;
import com.leadgen.backend.service.TestServiceInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report/")
public class ReportUserController extends GenericController<ReportUserDTO> {

    public ReportUserController(RepostUserService repostUserService) {
        super(repostUserService);
    }
}

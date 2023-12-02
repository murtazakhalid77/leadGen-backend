package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.BidDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.service.BidService;
import com.leadgen.backend.service.TestServiceInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bid/")
public class BidController extends GenericController<BidDTO> {

    public BidController(BidService bidService) {
        super(bidService);
    }
}

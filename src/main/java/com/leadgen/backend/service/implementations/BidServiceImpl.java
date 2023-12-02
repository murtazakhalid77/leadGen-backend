package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.BidDTO;
import com.leadgen.backend.model.Bid;
import com.leadgen.backend.repository.BidRepository;
import com.leadgen.backend.service.BidService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl extends GenericServiceImpl<Bid, BidDTO> implements BidService {

    @Autowired
    public BidServiceImpl(BidRepository bidRepository, ModelMapper modelMapper) {
        super(bidRepository, modelMapper, Bid.class, BidDTO.class);
    }
}

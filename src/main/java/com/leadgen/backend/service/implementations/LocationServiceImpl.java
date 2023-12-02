package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.LocationDTO;
import com.leadgen.backend.model.Location;
import com.leadgen.backend.service.GenericService;
import com.leadgen.backend.service.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends GenericServiceImpl<Location, LocationDTO> implements LocationService {
    @Autowired
    public LocationServiceImpl(JpaRepository<Location, Long> repository, ModelMapper modelMapper) {
        super(repository, modelMapper, Location.class, LocationDTO.class);
    }
}

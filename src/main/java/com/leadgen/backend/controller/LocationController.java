package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.LocationDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.service.LocationService;
import com.leadgen.backend.service.TestServiceInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location/")
public class LocationController extends GenericController<LocationDTO> {

    public LocationController(LocationService locationService) {
        super(locationService);
    }
}

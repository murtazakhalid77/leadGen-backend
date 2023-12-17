package com.leadgen.backend.controller;

import com.leadgen.backend.Dto.LocationDTO;
import com.leadgen.backend.Dto.TestDTO;
import com.leadgen.backend.model.Location;
import com.leadgen.backend.service.LocationService;
import com.leadgen.backend.service.TestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location/")
public class LocationController extends GenericController<LocationDTO> {

    public LocationController(LocationService locationService) {
        super(locationService);
    }


    @Autowired
    private LocationService locationService;

    @PostMapping("/save/{number}")
    public ResponseEntity<String> saveLocationForUser(@RequestBody LocationDTO locationDTO, @PathVariable String number) {
        locationService.saveLocationForUser(locationDTO, number);
        return ResponseEntity.ok("Location saved successfully for the user.");
    }
}

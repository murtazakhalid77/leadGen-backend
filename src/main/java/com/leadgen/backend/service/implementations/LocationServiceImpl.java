package com.leadgen.backend.service.implementations;

import com.leadgen.backend.Dto.LocationDTO;
import com.leadgen.backend.model.Location;
import com.leadgen.backend.model.User;
import com.leadgen.backend.repository.LocationRepository;
import com.leadgen.backend.repository.UserRepository;
import com.leadgen.backend.service.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.leadgen.backend.helpers.HelperClass.formatPhoneNumber;

@Service

public class LocationServiceImpl extends GenericServiceImpl<Location, LocationDTO> implements LocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    public LocationServiceImpl(JpaRepository<Location, Long> repository, ModelMapper modelMapper) {
        super(repository, modelMapper, Location.class, LocationDTO.class);
    }

    @Override
    public void saveLocationForUser(LocationDTO locationDTO, String number) {
        Location location = modelMapper.map(locationDTO, Location.class);
        Location savedLocation = locationRepository.save(location);

        User user = userRepository.findByPhoneNumber(formatPhoneNumber(number))
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Location> userLocations = new ArrayList<>();
        userLocations.add(savedLocation); // Add the new location to the list
        user.setDeviceId(locationDTO.getDeviceId());


        userRepository.save(user);
    }
}

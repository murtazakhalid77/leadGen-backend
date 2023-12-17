package com.leadgen.backend.service;

import com.leadgen.backend.Dto.LocationDTO;
import com.leadgen.backend.model.Location;
import com.leadgen.backend.service.GenericService;

public interface LocationService extends GenericService<LocationDTO> {

    void saveLocationForUser(LocationDTO locationDTO, String number);
}

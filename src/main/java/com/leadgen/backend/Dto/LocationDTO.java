package com.leadgen.backend.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDTO {
    private Long id;

    private String locality;

    private String subLocality;

    private String street;

    private String country;
    private String SubAdministrativeArea;
    private String administrativeArea;
    private String deviceId;
}

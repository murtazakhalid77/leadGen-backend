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


    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotEmpty(message = "Area cannot be empty")
    private String area;

    @NotEmpty(message = "State cannot be empty")
    private String state;

    @NotNull(message = "Postal code cannot be null")
    private Integer postalCode;

    @NotEmpty(message = "Country cannot be empty")
    private String country;

    @NotNull(message = "Latitude cannot be null")
    private Double latitude;

    @NotNull(message = "Longitude cannot be null")
    private Double longitude;

    @NotNull(message = "Status cannot be null")
    private Boolean status;


}

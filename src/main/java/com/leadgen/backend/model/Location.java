package com.leadgen.backend.model;

import javax.persistence.*;
import java.util.List;

public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String city;
    private String area;
    private String state;
    private Integer postalCode;
    private String country;
    private Double latitude;
    private Double longitude;
    private Boolean status;
}

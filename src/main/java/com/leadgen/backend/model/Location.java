package com.leadgen.backend.model;

import com.leadgen.backend.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Location extends Auditable {
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

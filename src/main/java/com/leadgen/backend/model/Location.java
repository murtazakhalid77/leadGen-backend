package com.leadgen.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leadgen.backend.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Location extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locality;

    private String subLocality;

    private String street;

    private String country;
    private String SubAdministrativeArea;
    private String administrativeArea;
}

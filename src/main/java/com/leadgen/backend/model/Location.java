package com.leadgen.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String locality;

    private String subLocality;

    private String street;

    private String country;
    private String SubAdministrativeArea;
    private String administrativeArea;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Name of the foreign key column in the Location table
    private User user;

}
